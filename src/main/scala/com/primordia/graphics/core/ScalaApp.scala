package com.primordia.graphics.core

import com.primordia.graphics.model.AppContext

trait ScalaApp extends App {

  protected val screen_points: Array[Float] = Array(
    -1.0f, 1.0f, 0.0f,
    1.0f, 1.0f, 0.0f,
    1.0f, -1.0f, 0.0f,
    -1.0f, -1.0f, 0.0f
  )

  val appContext: AppContext
  override protected def getAppContext: AppContext = appContext
}
