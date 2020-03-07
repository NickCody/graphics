package com.primordia.core

import java.nio.IntBuffer

import com.primordia.model.GlfwAppContext
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{GLFWFramebufferSizeCallback, GLFWVidMode}
import org.lwjgl.opengl.GL.setCapabilities
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT, GL_DEPTH_TEST, glClear, glClearColor, glEnable, glViewport}
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.memAddress
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
        glViewport(0, 0, windowWidth, windowHeight)
        onRender()
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

    // Center App Window
    val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor)
    glfwSetWindowPos(
      appContext.window, (vidmode.width - appContext.windowParams.width) / 2, (vidmode.height - appContext.windowParams.height) / 2)
    try {
      val frame = MemoryStack.stackPush
      try {
        val framebufferSize = frame.mallocInt(2)
        nglfwGetFramebufferSize(appContext.window, memAddress(framebufferSize), memAddress(framebufferSize) + 4)
        windowWidth = framebufferSize.get(0)
        windowHeight = framebufferSize.get(1)
      } finally if (frame != null) frame.close()
    } catch {
      case e: Exception => e.printStackTrace()
    }

    // Enable v-sync
    glfwSwapInterval(1)

    glfwShowWindow(appContext.window)

    while ( !glfwWindowShouldClose(appContext.window) ) {

      glfwPollEvents()

      onRender()

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
