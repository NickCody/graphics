package com.primordia.app

import com.primordia.core.{AppFactory, ScalaApp}
import com.primordia.model.{AppContext, Color, WindowParams}
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._
import org.lwjgl.system.MemoryUtil.NULL
import com.primordia.conversions.GlImplicit._
import com.primordia.util.GLHelpers
import org.lwjgl.opengl.{GL, GL11, GLCapabilities}


object ShaderTriangle {
  def main(args: Array[String]): Unit = {

    val app = new ShaderTriangle(
      AppFactory.createAppContext(
        WindowParams
          .defaultWindowParams()
          .title("Triangle")
          .backgroundColor(Color.Black)
          .multiSamples(8)
      ))

    app.run()
  }
}

class ShaderTriangle(override val appContext: AppContext) extends ScalaApp {

  private val points: Array[Float] = Array(
     0.0f,  0.75f, 0.0f,
     0.75f, -0.75f, 0.0f,
    -0.75f, -0.75f, 0.0f)

  var shader_prog: Int = 0
  var vao: Int = 0
  var u_resolution = 0
  var u_mouse = 0

  override def onBeforeInit(): Unit = {
    super.onBeforeInit()

    // Setup vertex buffer, vertex array + attributes
    vao = GLHelpers.createVertexArray3f(points)

    // Shader Setup
    //
    val vs = GLHelpers.generateVertexShader(GLHelpers.loadResource("shaders/SimplePosition.vs"))
    val fs = GLHelpers.generateFragmentShader(GLHelpers.loadResource("shaders/Highlight.fs"));

    shader_prog = GLHelpers.createShaderProgram(List(vs, fs).toArray)

    // Uniform setup
    //
    u_resolution = glGetUniformLocation(shader_prog, "u_resolution")
    u_mouse = glGetUniformLocation(shader_prog, "u_mouse")

    glUseProgram(shader_prog)

  }

  override def onRender(): Unit = {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glUniform2f(u_resolution, windowWidth.toFloat, windowHeight.toFloat)
    glUniform2f(u_mouse, mouseX.toFloat, windowHeight - mouseY.toFloat)

    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES, 0, 3)

    glfwSwapBuffers(getAppContext.getWindow)
  }

}
