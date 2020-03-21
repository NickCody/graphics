package com.primordia.app

import com.primordia.core.{AppFactory, ScalaApp}
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
          .title("LavaLamp")
          .backgroundColor(Color.Black)
          .multiSamples(32)
          .fullScreen(false)
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
  var rotated_scale: Float = 0.02f
  var primary_scale: Float = 0.002f

  var u_rot_left_divisor = 0
  var u_rot_right_divisor = 0

  var rot_left_divisor: Float = 3
  var rot_right_divisor: Float = 3

  var vao = 0;

  override def onBeforeInit(): Unit = {


    val keyCallback = new GLFWKeyCallback() {
      override def invoke(window: Long, key: Int, scancode: Int, action: Int, mods: Int): Unit = {
        if (action == GLFW_RELEASE) {
          log.info(s"key=$key, scancode=$scancode")
          key match {
            case 'E' => rotated_scale = rotated_scale / 2.0f
            case 'R' => rotated_scale = rotated_scale * 2.0f
            case 'O' => primary_scale = primary_scale / 2.0f
            case 'P' => primary_scale = primary_scale * 2.0f
            case 'F' => rot_left_divisor = rot_left_divisor + 5.0f;
            case 'D' => rot_left_divisor = rot_left_divisor - 5.0f;
            case 'L' => rot_right_divisor = rot_right_divisor + 5.0f;
            case 'K' => rot_right_divisor = rot_right_divisor - 5.0f;
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
  }

  override def onRender(): Unit = {

    glUniform1f(u_time, glfwGetTime().toFloat);
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
    log.info(s"rotated_scale=$rotated_scale, primary_scale=$primary_scale")
  }
}
