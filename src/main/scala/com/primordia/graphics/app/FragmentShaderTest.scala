package com.primordia.graphics.app

import com.primordia.graphics.core.{AppFactory, ScalaApp}
import com.primordia.graphics.model.{AppContext, Color, WindowParams}
import com.primordia.util.GLHelpers
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._


//
// A very basic OpenGL program to test various basic fragment shaders
//
object FragmentShaderTest {
  val fragmentShader: String = System.getProperty("fragmentShader", "shaders/ColorPulse.frag" )
  val multiSample: Int = System.getProperty("multiSample", "4" ).toInt

  def main(args: Array[String]): Unit = {

    val app = new FragmentShaderTest(
      AppFactory.createAppContext(
        WindowParams
          .defaultWindowParams()
          .title(fragmentShader)
          .multiSamples(multiSample)
          .width(1200)
          .height(1200)
          .fullScreen(true)
      ))

    app.run()
  }
}

class FragmentShaderTest(override val appContext: AppContext) extends ScalaApp {

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
  var vao = 0;

  override def onBeforeInit(): Unit = {

    val vbo_points = glGenBuffers
    glBindBuffer(GL_ARRAY_BUFFER, vbo_points)
    val fb_points = BufferUtils.createFloatBuffer(points.length).put(points)
    glBufferData(GL_ARRAY_BUFFER, fb_points.flip, GL_STATIC_DRAW)

    if (!glIsBuffer(vbo_points)) throw new RuntimeException("vbo_points is not a buffer!")

    vao = glGenVertexArrays();
    glBindVertexArray(vao);

    if (!glIsVertexArray(vao)) throw new RuntimeException("vao is not a vertex array!");

    glBindBuffer(GL_ARRAY_BUFFER, vbo_points);
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);
    glEnableVertexAttribArray(0);


    // Shader Setup
    //
    val vs = GLHelpers.generateVertexShader(GLHelpers.loadResource("shaders/SimplePosition.vert"))
    val fs = GLHelpers.generateFragmentShader(GLHelpers.loadResource(FragmentShaderTest.fragmentShader));
    shader_prog = GLHelpers.createShaderProgram(List(vs, fs).toArray)
    glUseProgram(shader_prog)

    // Uniform setup
    //
    iTime = glGetUniformLocation(shader_prog, "iTime")
    iResolution = glGetUniformLocation(shader_prog, "iResolution")
    iMouse = glGetUniformLocation(shader_prog, "iMouse")
  }

  override def onRender(): Unit = {

    glUniform1f(iTime, glfwGetTime().toFloat);
    glUniform2f(iResolution, windowWidth.toFloat, windowHeight.toFloat)
    glUniform2f(iMouse, mouseX.toFloat, windowHeight - mouseY.toFloat)

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glBindVertexArray(vao)
    glDrawArrays(GL_POLYGON, 0, 4)

    glfwSwapBuffers(getAppContext.getWindow)
  }

}
