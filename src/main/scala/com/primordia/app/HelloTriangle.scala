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


object HelloTriangle {
  def main(args: Array[String]): Unit = {

    val app = new HelloTriangle(
      AppFactory.createAppContext(
        WindowParams
          .defaultWindowParams()
          .title("Triangle")
          .backgroundColor(Color.Black)
          .multiSamples(4)
      ))

    app.run()
  }
}

class HelloTriangle(override val appContext: AppContext) extends ScalaApp {

  private val points: Array[Float] = Array(
     0.0f,  0.5f, 0.0f,
     0.5f, -0.5f, 0.0f,
    -0.5f, -0.5f, 0.0f)

  private val vertex_shader = GLHelpers.loadResource("shaders/SimplePosition.vs")
  private val fragment_shader = GLHelpers.loadResource("shaders/Highlight.fs")

  var shader_prog: Int = 0
  var vao: Int = 0
  var u_resolution = 0
  var u_mouse = 0

  override def onInit(): Unit = {
    super.onInit()

    glDepthFunc(GL_LESS)

    // Setup vertex buffer, vertex array + attributes
    vao = GLHelpers.createVertexArray3f(points)

    // Shader Setup
    //
    val vs = GLHelpers.generateVertexShader(vertex_shader)
    val fs = GLHelpers.generateFragmentShader(fragment_shader);

    shader_prog = glCreateProgram
    glAttachShader(shader_prog, vs)
    glAttachShader(shader_prog, fs)
    glLinkProgram(shader_prog)

    u_resolution = glGetUniformLocation(shader_prog, "u_resolution")
    u_mouse = glGetUniformLocation(shader_prog, "u_mouse")

    if (!glIsProgram(shader_prog)) {
      throw new RuntimeException("shader_prog is not a shader program!")
    }

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
