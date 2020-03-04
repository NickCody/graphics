package com.primordia.core

import com.primordia.model.GlfwAppContext
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL.setCapabilities
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT, glClear, glClearColor}
import org.slf4j.LoggerFactory

trait GlfwApp {
  val appContext: GlfwAppContext

  private val log = LoggerFactory.getLogger(this.getClass)

  def onInit(): Unit = {
    log.info("GlfwApp::onInit")
  }

  def onExit(): Unit = {
    log.info("GlfwApp::onExit")
    glfwTerminate()
  }

  def onRender(): Unit

  def run(): Unit = {
    log.info("GlfwApp::run")

    onInit()

    glfwShowWindow(appContext.window)

    while ( !glfwWindowShouldClose(appContext.window) ) {
      setCapabilities(appContext.glCaps)
      glClearColor(
        appContext.windowParams.backgroundColor.r,
        appContext.windowParams.backgroundColor.g,
        appContext.windowParams.backgroundColor.b,
        appContext.windowParams.backgroundColor.a)
      glClear(GL_COLOR_BUFFER_BIT)

      onRender()

      glfwSwapBuffers(appContext.window)

      glfwPollEvents()
    }

    onExit()
  }

}
