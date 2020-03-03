package com.primordia.util

import org.lwjgl.glfw.GLFW.{GLFW_FALSE, GLFW_RESIZABLE, glfwCreateWindow, glfwInit, glfwMakeContextCurrent, glfwWindowHint}
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GLCapabilities
import org.lwjgl.system.MemoryUtil.NULL

object ApplicationFactory {
  def defaultWindowParams: WindowParams = WindowParams("App", 800, 600, Color(1f, 1f, 1f))

  def createAppContext(wParams: WindowParams = defaultWindowParams): GlfwAppContext = {

    glfwInit

    glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)

    val window = glfwCreateWindow(wParams.width, wParams.height, wParams.title, NULL, NULL)
    glfwMakeContextCurrent(window)

    val caps: GLCapabilities =  createCapabilities()

    GlfwAppContext(window, caps)
  }

}
