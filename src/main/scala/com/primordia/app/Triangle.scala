package com.primordia.app

import java.nio.{FloatBuffer, IntBuffer}

import com.primordia.core.{GlfwApp, GlfwAppFactory}
import com.primordia.model.{Color, GlfwAppContext, WindowParams}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11.{GL_FLOAT, GL_PROJECTION, GL_TRIANGLES, GL_UNSIGNED_INT, GL_VERTEX_ARRAY, glDrawElements, glEnableClientState, glLoadIdentity, glMatrixMode, glOrtho, glVertexPointer}
import org.lwjgl.opengl.GL15.{GL_ARRAY_BUFFER, GL_ELEMENT_ARRAY_BUFFER, GL_STATIC_DRAW, glBindBuffer, glBufferData, glGenBuffers}

object Triangle {
  def main(args: Array[String]): Unit = {
    new Triangle(
      GlfwAppFactory.createAppContext(
        WindowParams("Triangle", 1920, 1080, Color.AquaMarine, List("Green32x32.png", "Green64x64.png")))
    ).run()
  }
}

class Triangle(val appContext: GlfwAppContext) extends GlfwApp {
  val vbo = glGenBuffers
  val ibo = glGenBuffers
  val vertices = Array(-0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f)
  val indices = Array(0, 1, 2)
  glBindBuffer(GL_ARRAY_BUFFER, vbo)
  glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip.asInstanceOf[FloatBuffer], GL_STATIC_DRAW)
  glEnableClientState(GL_VERTEX_ARRAY)
  glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
  glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices.length).put(indices).flip.asInstanceOf[IntBuffer], GL_STATIC_DRAW)
  glVertexPointer(2, GL_FLOAT, 0, 0L)

  override def onRender(): Unit = {
    glMatrixMode(GL_PROJECTION)
    val aspect = windowWidth.toFloat / windowHeight.toFloat
    glLoadIdentity()
    glOrtho(-aspect, aspect, -1, 1, -1, 1)
    glDrawElements(GL_TRIANGLES, 3, GL_UNSIGNED_INT, 0L)
  }

  override def onInit(): Unit = {

    super.onInit()
  }
}