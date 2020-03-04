package com.primordia.core

import com.primordia.model.GlfwAppContext
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWFramebufferSizeCallback
import org.lwjgl.opengl.GL.setCapabilities
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT, GL_DEPTH_TEST, glClear, glClearColor, glEnable, glViewport}
import org.slf4j.LoggerFactory

trait GlfwApp {
  private val log = LoggerFactory.getLogger(this.getClass)

  val appContext: GlfwAppContext

  var windowWidth: Int = 0
  var windowHeight: Int = 0
  var sizeCallback: GLFWFramebufferSizeCallback = new GLFWFramebufferSizeCallback() {
    override def invoke(window: Long, width: Int, height: Int): Unit = {
      if (width > 0 && height > 0 && (windowWidth != width || windowHeight != height)) {
        windowWidth = width
        windowHeight = height
      }
    }
  }

  def onInit(): Unit = {
    log.info("GlfwApp::onInit")
  }

  def onExit(): Unit = {
    log.info("GlfwApp::onExit")
  }

  def onRender(): Unit

  def run(): Unit = {
    log.info("GlfwApp::run")

    onInit()

    glfwSetFramebufferSizeCallback(appContext.window, sizeCallback)

    glClearColor(
      appContext.windowParams.backgroundColor.r,
      appContext.windowParams.backgroundColor.g,
      appContext.windowParams.backgroundColor.b,
      appContext.windowParams.backgroundColor.a)
    glEnable(GL_DEPTH_TEST)

    glfwShowWindow(appContext.window)

    while ( !glfwWindowShouldClose(appContext.window) ) {

      glfwPollEvents()
      glViewport(0, 0, windowWidth, windowHeight)
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

      onRender()

      glfwSwapBuffers(appContext.window)
    }

    onExit()

    try {
      sizeCallback.free();
      glfwDestroyWindow(appContext.window)
    } catch {
      case t: Throwable => t.printStackTrace()
    } finally {
      glfwTerminate()
    }
  }

}
