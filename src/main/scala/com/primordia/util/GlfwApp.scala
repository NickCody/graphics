package com.primordia.util

import ch.qos.logback.classic.{Logger, LoggerContext}
import ch.qos.logback.core.util.StatusPrinter
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, GL_DEPTH_BUFFER_BIT, glClear, glClearColor}
import org.slf4j.LoggerFactory

trait GlfwApp {
  val appContext: GlfwAppContext
  val windowParams: WindowParams

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

    while ( !glfwWindowShouldClose(appContext.window) ) {
      glClearColor(windowParams.backgroundColor.r, windowParams.backgroundColor.g, windowParams.backgroundColor.b, 1.0f)
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)

      onRender()

      glfwSwapBuffers(appContext.window)

      glfwPollEvents()
    }

    onExit()
  }

}
