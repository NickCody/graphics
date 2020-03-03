package com.primordia.app

import com.primordia.util.{ApplicationFactory, Color, GlfwApp, GlfwAppContext, WindowParams}


object HelloScala {

  def main(args: Array[String]): Unit = {
    val windowParams = WindowParams("HelloScala", 1920, 1080, Color(0.5f, 0.5f, 0.5f))
    val appContext = ApplicationFactory.createAppContext(windowParams)
    val app = new HelloScala(windowParams, appContext)
    app.run()
  }


}

class HelloScala(val windowParams: WindowParams, val appContext: GlfwAppContext) extends GlfwApp {

  override def onRender(): Unit = {

  }

  override def onInit(): Unit = {
    super.onInit()
  }
}