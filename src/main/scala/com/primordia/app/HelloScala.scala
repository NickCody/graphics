package com.primordia.app

import com.primordia.core.{GlfwApp, GlfwAppFactory}
import com.primordia.model.{Color, GlfwAppContext, WindowParams}

object HelloScala {
  def main(args: Array[String]): Unit = {
    new HelloScala(
      GlfwAppFactory.createAppContext(
        WindowParams("HelloScala", 1920, 1080, Color.AquaMarine, List("Green32x32.png", "Green64x64.png")))
    ).run()
  }
}

class HelloScala(val appContext: GlfwAppContext) extends GlfwApp {
  override def onRender(): Unit = {
  }

  override def onInit(): Unit = {

    super.onInit()
  }
}