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
      ))

    app.run()
  }
}

class HelloTriangle(override val appContext: AppContext) extends ScalaApp {

  private val points: Array[Float] = Array(
     0.0f,  0.5f, 0.0f,
     0.5f, -0.5f, 0.0f,
    -0.5f, -0.5f, 0.0f)

  private val vertex_shader =
    """
      |in vec3 vp;
      |void main() {
      |    gl_Position = vec4(vp, 1.0);
      |}
      |""".stripMargin

  private val fragment_shader =
    s"""
      |#ifdef GL_ES
      |precision mediump float;
      |#endif
      |
      |uniform vec2 u_resolution;
      |uniform vec2 u_mouse;
      |
      |#define FALLOFF 800
      |
      |void main() {
      | float distMouse = min(FALLOFF, distance(gl_FragCoord, u_mouse));
      | float distZero = distance(gl_FragCoord, vec2(0,0))/u_resolution;
      | vec3 color = mix(${Color.Blues_powderblue.vec3}, ${Color.Purples_blueviolet.vec3}, distZero);
      | vec3 final = mix(color, ${Color.White.vec3}, 1.0-(distMouse/FALLOFF));
      |	gl_FragColor = vec4(final,1.0);
      |}
      |
      |""".stripMargin

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

  }

  override def onRender(): Unit = {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glUseProgram(shader_prog)
    glUniform2f(u_resolution, windowWidth.toFloat, windowHeight.toFloat)
    glUniform2f(u_mouse, mouseX.toFloat, windowHeight - mouseY.toFloat)
    glBindVertexArray(vao)
    glDrawArrays(GL_TRIANGLES, 0, 3)

    // update other events like input handling
    glfwSwapBuffers(getAppContext.getWindow)
  }

}
