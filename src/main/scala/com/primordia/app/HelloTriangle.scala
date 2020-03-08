package com.primordia.app

import com.primordia.core.{AppFactory, ScalaApp}
import com.primordia.model.{AppContext, Color, WindowParams}


object HelloTriangle {
  def main(args: Array[String]): Unit = {
    new HelloTriangle(AppFactory.createAppContext("HelloTriangle")).run()
  }
}
class HelloTriangle(override val appContext: AppContext) extends ScalaApp {


}
