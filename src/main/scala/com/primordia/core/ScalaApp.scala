package com.primordia.core

import com.primordia.model.AppContext

trait ScalaApp extends App {

  val appContext: AppContext
  override protected def getAppContext: AppContext = appContext;
}
