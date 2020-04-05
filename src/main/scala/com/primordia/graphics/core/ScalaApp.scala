package com.primordia.graphics.core

import com.primordia.graphics.model.AppContext

trait ScalaApp extends App {

  val appContext: AppContext
  override protected def getAppContext: AppContext = appContext;
}
