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
import org.lwjgl.opengl.{GL, GL11, GLCapabilities}
import org.lwjgl.opengl.ARBFragmentShader._
import org.lwjgl.opengl.ARBShaderObjects._
import org.lwjgl.opengl.ARBVertexShader._

object HelloTriangle {
  def main(args: Array[String]): Unit = {

    val app = new HelloTriangle(
      AppFactory.createAppContext(
        WindowParams
          .defaultWindowParams()
          .title("Triangle")
          .backgroundColor(Color.Black)
      ))

    app.run()
  }
}

class HelloTriangle(override val appContext: AppContext) extends ScalaApp {

  private val points: Array[Float] = Array(
     0.0f,  0.5f, 0.0f,
     0.5f, -0.5f, 0.0f,
    -0.5f, -0.5f, 0.0f)

  private val octogonPoints: Array[Float] = Array(
    -1.0f, 2.0f, 0.0f,
     1.0f, 2.0f, 0.0f,
     2.0f, 1.0f, 0.0f,
    2.0f, -1.0f, 0.0f,
    1.0f, -2.0f, 0.0f,
    -1.0f, -2.0f, 0.0f,
    -2.0f, -1.0f, 0.0f,
    -2.0f, 1.0f, 0.0f)

  private val vertex_shader =
    """
      |#version 400
      |
      |in vec3 vp;
      |void main() {
      |    gl_Position = vec4(vp, 1.0);
      |}
      |""".stripMargin

  // ${Color.Blues_blue.vec4s()}
  private val fragment_shader =
    s"""
      |#version 400\n
      |
      |out vec4 frag_color;
      |void main() {
      |    frag_color = ${Color.Blues_darkslateblue.vec4s()});
      |}
      |""".stripMargin

  println(fragment_shader)

  var shader_prog: Int = 0
  var vao: Int = 0

  override def onInit(): Unit = {
    super.onInit()

    glDepthFunc(GL_LESS)

    // Setup Array Buffer
    val vbo = glGenBuffers
    glBindBuffer(GL_ARRAY_BUFFER, vbo);
    glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(points.length).put(points).flip(), GL_STATIC_DRAW);
    if (!glIsBuffer(vbo)) {
      throw new RuntimeException("vbo is not a buffer!")
    }

    // Setup vertex array
    vao = glGenVertexArrays
    glBindVertexArray(vao)
    if (!glIsVertexArray(vao)) {
      throw new RuntimeException("vao is not a vertex array!")
    }

    glEnableVertexAttribArray(0)
    glBindBuffer(GL_ARRAY_BUFFER, vbo)
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L)

    // Shader Setup
    //
    val vs = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vs, vertex_shader);
    glCompileShader(vs);
    if (!glIsShader(vs) || glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new RuntimeException("vs is not a vertext shader or not compiled!")
    }

    val fs = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fs, fragment_shader);
    glCompileShader(fs);
    if (!glIsShader(fs) || glGetShaderi(fs, GL_COMPILE_STATUS) == GL_FALSE) {
      throw new RuntimeException("fs is not a vertext shader or not compiled!")
    }

    shader_prog = glCreateProgram
    glAttachShader(shader_prog, fs)
    glAttachShader(shader_prog, vs)
    glLinkProgram(shader_prog)

    if (!glIsProgram(shader_prog)) {
      throw new RuntimeException("shader_prog is not a shader program!")
    }

  }

  override def onRender(): Unit = {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glUseProgram(shader_prog)
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES, 0, 3)

    // update other events like input handling
    glfwSwapBuffers(getAppContext.getWindow)
  }

}
