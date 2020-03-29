package com.primordia.app.triangles

import com.primordia.core.{AppFactory, ScalaApp}
import com.primordia.model.{AppContext, Color, WindowParams}
import com.primordia.util.GLHelpers
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._
import org.lwjgl.opengl.GL20._
import org.lwjgl.opengl.GL30._

object ColorTriangle {
  def main(args: Array[String]): Unit = {

    val app = new ColorTriangle(
      AppFactory.createAppContext(
        WindowParams
          .defaultWindowParams()
          .title("ColorTriangle")
          .backgroundColor(Color.Black)
      ))

    app.run()
  }
}

class ColorTriangle(override val appContext: AppContext) extends ScalaApp {

  private val points: Array[Float] = Array(
     0.0f,  0.75f, 0.0f,
     0.75f, -0.75f, 0.0f,
    -0.75f, -0.75f, 0.0f)

  private val colors: Array[Float] = Array(
    1.0f, 0.0f, 0.0f,
    0.0f, 1.0f, 0.0f,
    0.0f, 0.0f, 1.0f)

  var shader_prog: Int = 0
  var vao: Int = 0
  var u_resolution = 0
  var u_mouse = 0

  override def onBeforeInit(): Unit = {

    val vbo_points = glGenBuffers
    glBindBuffer(GL_ARRAY_BUFFER, vbo_points)
    val fb_points = BufferUtils.createFloatBuffer(points.length).put(points)
    glBufferData(GL_ARRAY_BUFFER, fb_points.flip, GL_STATIC_DRAW)
    if (!glIsBuffer(vbo_points)) throw new RuntimeException("vbo_points is not a buffer!")

    val vbo_colors = glGenBuffers
    glBindBuffer(GL_ARRAY_BUFFER, vbo_colors)
    val fb_colors = BufferUtils.createFloatBuffer(colors.length).put(colors)
    glBufferData(GL_ARRAY_BUFFER, fb_colors.flip, GL_STATIC_DRAW)
    if (!glIsBuffer(vbo_colors)) throw new RuntimeException("vbo_colors is not a buffer!")

    vao = glGenVertexArrays();
    glBindVertexArray(vao);
    if (!glIsVertexArray(vao)) {
      throw new RuntimeException("vao is not a vertex array!");
    }

    glBindBuffer(GL_ARRAY_BUFFER, vbo_points);
    glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0L);
    glBindBuffer(GL_ARRAY_BUFFER, vbo_colors);
    glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0L);

    glEnableVertexAttribArray(0);
    glEnableVertexAttribArray(1);

    // Shader Setup
    //
    val vs = GLHelpers.generateVertexShader(GLHelpers.loadResource("shaders/ColorAndPosition.vert"))
    val fs = GLHelpers.generateFragmentShader(GLHelpers.loadResource("shaders/ColorInput.frag"));

    shader_prog = createShaderProgram(List(vs, fs).toArray)

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

  def createShaderProgram(shaders: Array[Integer]): Integer = {
    val shader_prog = glCreateProgram

    for (shader <- shaders) {
      glAttachShader(shader_prog, shader)
      glAttachShader(shader_prog, shader)
    }

    glBindAttribLocation(shader_prog, 0, "vertex_position")
    glBindAttribLocation(shader_prog, 1, "vertex_color")

    glLinkProgram(shader_prog)

    if (!glIsProgram(shader_prog)) throw new RuntimeException("Could not link shader program!")

    shader_prog
  }

}
