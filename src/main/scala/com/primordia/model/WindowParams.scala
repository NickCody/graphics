package com.primordia.model


case class WindowParams(
  title: String = "App",
  width: Int = 1920,
  height: Int = 1080,
  backgroundColor: Color = Color.Beige,
  icons: List[String] = List())
