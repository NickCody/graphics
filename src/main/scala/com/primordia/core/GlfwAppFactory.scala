package com.primordia.core

import com.primordia.model.{GlfwAppContext, WindowParams}
import com.primordia.util.WindowIconLoader
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GLCapabilities
import org.lwjgl.system.MemoryUtil.NULL

object GlfwAppFactory {

  def createAppContext(title: String): GlfwAppContext = {
    createAppContext(WindowParams(title))
  }

  def createAppContext(wParams: WindowParams = WindowParams()): GlfwAppContext = {

    if (!glfwInit) throw new IllegalStateException("Unable to initialize GLFW")

    glfwDefaultWindowHints()
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)

    val window = glfwCreateWindow(wParams.width, wParams.height, wParams.title, NULL, NULL)

    WindowIconLoader.setIcons(window, wParams.icons.toArray)

    glfwMakeContextCurrent(window)

    val caps: GLCapabilities =  createCapabilities()

    GlfwAppContext(window, caps, wParams)
  }

}
