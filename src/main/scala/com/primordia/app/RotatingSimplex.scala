package com.primordia.app

import com.primordia.core.{AppFactory, ScalaApp, SwtWindow}
import com.primordia.model.{AppContext, Color, WindowParams}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import com.primordia.util.GLHelpers
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.opengl.GL15.{GL_ARRAY_BUFFER, GL_STATIC_DRAW, glBindBuffer, glBufferData, glGenBuffers, glIsBuffer}


object RotatingSimplex {
  def main(args: Array[String]): Unit = {
    val app = new RotatingSimplex(
      AppFactory.createAppContext(
        WindowParams
          .defaultWindowParams()
          .title("Rotating Simplex Noise")
          .backgroundColor(Color.Black)
          .multiSamples(Int.MaxValue),
        true
      ))

    app.run()
  }
}

class RotatingSimplex(override val appContext: AppContext) extends ScalaApp {

  private val points: Array[Float] = Array(
    -1.0f,  1.0f,  0.0f,
     1.0f,  1.0f,  0.0f,
     1.0f, -1.0f,  0.0f,
    -1.0f,  -1.0f, 0.0f
    )

  var shader_prog: Int = 0
  var u_time = 0
  var u_resolution = 0

  var u_rotated_scale = 0
  var u_primary_scale = 0
  var rotated_scale: Float = 0.01f
  var primary_scale: Float = 0.004f

  var u_rot_left_divisor = 0
  var u_rot_right_divisor = 0
  var rot_left_divisor: Float = 17
  var rot_right_divisor: Float = 23

  var u_animated = 0
  var animated = true
  var last_rendered_timecode: Float =  glfwGetTime().toFloat
  var delta_timecode: Float = 0
  var vao = 0;

  val optionsWindow = new SwtWindow(this,
    WindowParams.defaultWindowParams()
      .title("Options")
      .backgroundColor(Color.Blues_cornflowerblue)
      .width(800)
      .height(600))

  override def onBeforeInit(): Unit = {

    val keyCallback = new GLFWKeyCallback() {
      override def invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_RELEASE) {
          key match {
            case GLFW_KEY_E => rotated_scale = rotated_scale / 2.0f
            case GLFW_KEY_R => rotated_scale = rotated_scale * 2.0f
            case GLFW_KEY_O => primary_scale = primary_scale / 2.0f
            case GLFW_KEY_P => primary_scale = primary_scale * 2.0f
            case GLFW_KEY_F => rot_left_divisor = rot_left_divisor + 5.0f;
            case GLFW_KEY_D => rot_left_divisor = rot_left_divisor - 5.0f;
            case GLFW_KEY_L => rot_right_divisor = rot_right_divisor + 5.0f;
            case GLFW_KEY_K => rot_right_divisor = rot_right_divisor - 5.0f;
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
    val fs = GLHelpers.generateFragmentShader(GLHelpers.loadResource("shaders/perlin-noise/RotatingSimplex.frag"));
    shader_prog = GLHelpers.createShaderProgram(List(vs, fs).toArray)
    glUseProgram(shader_prog)

    // Uniform setup
    //
    u_resolution = glGetUniformLocation(shader_prog, "u_resolution")
    u_time = glGetUniformLocation(shader_prog, "u_time")
    u_rotated_scale = glGetUniformLocation(shader_prog, "u_rotated_scale")
    u_primary_scale = glGetUniformLocation(shader_prog, "u_primary_scale")
    u_rot_left_divisor = glGetUniformLocation(shader_prog, "u_rot_left_divisor")
    u_rot_right_divisor = glGetUniformLocation(shader_prog, "u_rot_right_divisor")

    auxWindows.add(optionsWindow);
  }

  override def onRender(): Unit = {

    if (animated) {
      last_rendered_timecode = glfwGetTime().toFloat - delta_timecode
    }

    glUniform1f(u_time, last_rendered_timecode );
    glUniform2f(u_resolution, windowWidth.toFloat, windowHeight.toFloat)
    glUniform1f(u_rotated_scale, rotated_scale);
    glUniform1f(u_primary_scale, primary_scale);
    glUniform1f(u_rot_left_divisor, rot_left_divisor);
    glUniform1f(u_rot_right_divisor, rot_right_divisor);

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
        |rot_left_divisor       = $rot_left_divisor
        |rot_right_divisor      = $rot_right_divisor
        |
        |delta_timecode         = $delta_timecode
        |last_rendered_timecode = $last_rendered_timecode
        |""".stripMargin

    log.info(params)
  }

  override def onExit(): Unit = {
    //optionsWindow.close()
  }
}
