package com.primordia.graphics.app

import com.primordia.graphics.core.{App, AppFactory, ScalaApp, SwtWindow}
import com.primordia.graphics.model.{AppContext, Color, WindowParams}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import com.primordia.util.GLHelpers
import org.eclipse.swt.events.{SelectionEvent, SelectionListener}
import org.eclipse.swt.graphics.Rectangle
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.{Composite, Control, Event, Listener, Scale}
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.opengl.GL15.{GL_ARRAY_BUFFER, GL_STATIC_DRAW, glBindBuffer, glBufferData, glGenBuffers, glIsBuffer}


object RotatingSimplex {
  val fragmentShader: String = System.getProperty("fragmentShader", "shaders/perlin-noise/RotatingSimplex.frag" )
  val multiSample: Int = System.getProperty("multiSample", "32" ).toInt
  val fullScreen: Boolean = System.getProperty("fullScreen", "true" ).matches("true|1")

  def main(args: Array[String]): Unit = {

    val windowParams = WindowParams
      .defaultWindowParams()
      .title("Rotating Simplex Noise")
      .backgroundColor(Color.Black)
      .multiSamples(multiSample)
      .fullScreen(fullScreen)
      .width(1920)
      .height(1080)

    val app = new RotatingSimplex(
      AppFactory.createAppContext(windowParams, true)
    )

    app.run()
  }
}

class RotatingSimplex(override val appContext: AppContext) extends ScalaApp {
  protected class MappedScale(scale: Scale, start: Float, stop: Float){
    def getScaledSelection: Float = {
      val controlRange = scale.getMaximum - scale.getMinimum
      val continuousRange = stop - start
      val rangeSel = (scale.getSelection.toFloat - scale.getMinimum.toFloat)/controlRange.toFloat
      rangeSel/continuousRange + start
    }

    def setScaledSelection(pos: Float): Unit = {
      val controlRange = scale.getMaximum - scale.getMinimum
      val continuousRange = stop - start

      val p = (pos-start)/continuousRange
      scale.setSelection((p*controlRange).toInt + scale.getMinimum)
    }
  }

  protected class OptionsWindow(app: RotatingSimplex, windowParams: WindowParams) extends SwtWindow(app, windowParams) {

    import org.eclipse.swt.SWT
    import org.eclipse.swt.widgets.Text
    val clientArea: Rectangle = shell.getClientArea

    val gridLayout = new GridLayout()
    gridLayout.numColumns = 2
    swtCanvas.setLayout(gridLayout)

    new Text(swtCanvas, SWT.SINGLE).setText("Rotated Scale")
    val rotatedScale = new Scale(swtCanvas, SWT.PUSH)
    val rotatedScaleMapped = new MappedScale(rotatedScale, 0.01f, .1f)
    rotatedScale.setBounds(clientArea.x, clientArea.y, clientArea.width, 64)
    rotatedScale.setMinimum(1)
    rotatedScale.setMaximum(20)
    rotatedScale.setPageIncrement(5)
    rotatedScaleMapped.setScaledSelection(app.getRotatedScale)
    rotatedScale.addSelectionListener(new SelectionListener {
      override def widgetSelected(e: SelectionEvent): Unit = {
        app.setRotatedScale(rotatedScaleMapped.getScaledSelection)
      }

      override def widgetDefaultSelected(e: SelectionEvent): Unit = {}
    } )

    new Text(swtCanvas, SWT.SINGLE).setText("Primary Scale")
    val primaryScale = new Scale(swtCanvas, SWT.PUSH)
    primaryScale.setBounds(clientArea.x, clientArea.y, clientArea.width, 64)
    primaryScale.setMinimum(1)
    primaryScale.setMaximum(100)
    primaryScale.setPageIncrement(5)

    swtCanvas.pack()


  }

  private val points: Array[Float] = Array(
    -1.0f,  1.0f,  0.0f,
     1.0f,  1.0f,  0.0f,
     1.0f, -1.0f,  0.0f,
    -1.0f,  -1.0f, 0.0f
    )

  var shader_prog: Int = 0
  var iTime = 0
  var iResolution = 0
  var iMouse = 0

  var u_rotated_scale = 0
  var u_primary_scale = 0
  var rotated_scale: Float = 0.01f
  var primary_scale: Float = 0.01f

  var u_timescale = 0
  var timescale = 0.1f

  var u_rot_left_timescale = 0
  var u_rot_right_timescale = 0
  var rot_left_timescale: Float = 0.01f
  var rot_right_timescale: Float = -0.01f

  var u_animated = 0
  var animated = true

  var u_showComponents = 0
  var showComponents = 0


  var last_rendered_timecode: Float =  glfwGetTime().toFloat
  var delta_timecode: Float = 0
  var vao = 0;

  val optionsWindow = new OptionsWindow(this,
    WindowParams.defaultWindowParams()
      .title("Options")
      .backgroundColor(Color.White)
      .width(800)
      .height(600))

  override def onBeforeInit(): Unit = {
    log.info("Dumping initial parameters: ")
    dumpParameters()

    val keyCallback = new GLFWKeyCallback() {
      override def invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_RELEASE) {
          key match {
            case GLFW_KEY_E => rotated_scale = rotated_scale / 2.0f
            case GLFW_KEY_R => rotated_scale = rotated_scale * 2.0f
            case GLFW_KEY_O => primary_scale = primary_scale / 2.0f
            case GLFW_KEY_P => primary_scale = primary_scale * 2.0f
            case GLFW_KEY_D => rot_left_timescale = rot_left_timescale / 2.0f;
            case GLFW_KEY_F => rot_left_timescale = Math.max(0.001f, rot_left_timescale * 2.0f);
            case GLFW_KEY_K => rot_right_timescale = rot_right_timescale / 2.0f;
            case GLFW_KEY_L => rot_right_timescale = Math.max(0.001f, rot_right_timescale * 2.0f);
            case GLFW_KEY_X => timescale = timescale / 2.0f;
            case GLFW_KEY_C => timescale = Math.max(0.001f, timescale * 2.0f);
            case GLFW_KEY_Z => showComponents = if (showComponents == 0) 1 else 0

            case GLFW_KEY_H =>
              optionsWindow.open()

            case GLFW_KEY_SPACE =>
              if (animated) {
                animated = false
              } else {
                animated = true
                delta_timecode = glfwGetTime().toFloat - last_rendered_timecode
              }

            case _ =>
          }
          dumpParameters()
        }
      }
    }

    glfwSetKeyCallback(getAppContext.getWindow, keyCallback)

    val vbo_points = glGenBuffers
    glBindBuffer(GL_ARRAY_BUFFER, vbo_points)
    val fb_points = BufferUtils.createFloatBuffer(points.length).put(points)
    glBufferData(GL_ARRAY_BUFFER, fb_points.flip, GL_STATIC_DRAW)
    if (!glIsBuffer(vbo_points)) throw new RuntimeException("vbo_points is not a buffer!")

    vao = glGenVertexArrays();
    glBindVertexArray(vao);
    if (!glIsVertexArray(vao)) {
      throw new RuntimeException("vao is not a vertex array!");
    }

    glBindBuffer(GL_ARRAY_BUFFER, vbo_points);
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);
    glEnableVertexAttribArray(0);


    // Shader Setup
    //
    val vs = GLHelpers.generateVertexShader(GLHelpers.loadResource("shaders/SimplePosition.vert"))
    val fs = GLHelpers.generateFragmentShader(GLHelpers.loadResource(RotatingSimplex.fragmentShader));
    shader_prog = GLHelpers.createShaderProgram(List(vs, fs).toArray)
    glUseProgram(shader_prog)

    // Uniform setup
    //
    iResolution = glGetUniformLocation(shader_prog, "iResolution")
    iTime = glGetUniformLocation(shader_prog, "iTime")
    iMouse = glGetUniformLocation(shader_prog, "iMouse")
    u_showComponents = glGetUniformLocation(shader_prog, "u_showComponents")
    u_rotated_scale = glGetUniformLocation(shader_prog, "u_rotated_scale")
    u_primary_scale = glGetUniformLocation(shader_prog, "u_primary_scale")
    u_timescale = glGetUniformLocation(shader_prog, "u_timescale")
    u_rot_left_timescale = glGetUniformLocation(shader_prog, "u_rot_left_timescale")
    u_rot_right_timescale = glGetUniformLocation(shader_prog, "u_rot_right_timescale")

    auxWindows.add(optionsWindow);
  }

  override def onRender(): Unit = {

    if (animated) {
      last_rendered_timecode = glfwGetTime().toFloat - delta_timecode
    }

    glUniform1f(iTime, last_rendered_timecode );
    glUniform2f(iResolution, windowWidth.toFloat, windowHeight.toFloat)
    glUniform2f(iMouse, mouseX.toFloat, windowHeight - mouseY.toFloat)
    glUniform1f(u_timescale, timescale)
    glUniform1i(u_showComponents, showComponents)
    glUniform1f(u_rotated_scale, rotated_scale)
    glUniform1f(u_primary_scale, primary_scale)
    glUniform1f(u_rot_left_timescale, rot_left_timescale)
    glUniform1f(u_rot_right_timescale, rot_right_timescale)

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glBindVertexArray(vao)
    glDrawArrays(GL_POLYGON, 0, 4)

    glfwSwapBuffers(getAppContext.getWindow)
  }

  def dumpParameters(): Unit = {
    val params =
      s"""
        | -=-=-=-=-=-==-=-=--
        |Parameter Output:
        | =-=-=-=-=-=-=-=--=-
        |rotated_scale          = $rotated_scale
        |primary_scale          = $primary_scale
        |timescale              = $timescale
        |rot_left_timescale     = $rot_left_timescale
        |rot_right_timescale    = $rot_right_timescale
        |
        |delta_timecode         = $delta_timecode
        |last_rendered_timecode = $last_rendered_timecode
        |""".stripMargin

    log.info(params)
  }

  override def onExit(): Unit = {
    //optionsWindow.close()
  }

  def getRotatedScale: Float = rotated_scale

  def setRotatedScale(newScale: Float): Unit = {
    rotated_scale = newScale
  }
}
