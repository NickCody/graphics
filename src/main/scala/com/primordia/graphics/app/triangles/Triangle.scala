package com.primordia.graphics.app.triangles

import com.primordia.graphics.core.{AppFactory, ScalaApp}
import com.primordia.graphics.model.{AppContext, WindowParams}
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.GL15._

object Triangle {
  def main(args: Array[String]): Unit = {
    new Triangle(AppFactory.createAppContext(
      WindowParams
        .defaultWindowParams()
        .title("Triangle")
    )).run()
  }
}

class Triangle(override val appContext: AppContext) extends ScalaApp {

  private val vbo = glGenBuffers
  private val ibo = glGenBuffers
  private val vertices = Array(-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f)
  private val indices = Array(0, 1, 2)

  glBindBuffer(GL_ARRAY_BUFFER, vbo)
  glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip, GL_STATIC_DRAW)
  glEnableClientState(GL_VERTEX_ARRAY)

  glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
  glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices.length).put(indices).flip, GL_STATIC_DRAW)
  glVertexPointer(2, GL_FLOAT, 0, 0L)

  override def onRender(): Unit = {

    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

    glMatrixMode(GL_PROJECTION)
    val aspect = windowWidth.toFloat / windowHeight.toFloat
    glLoadIdentity()
    glOrtho(-aspect, aspect, -1, 1, -1, 1)
    glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0L)

    glfwSwapBuffers(appContext.getWindow())
  }
}