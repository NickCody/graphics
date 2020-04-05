package com.primordia.graphics.conversions

import org.lwjgl.opengl.GL11.GL_TRUE

object GlImplicit {

  implicit def GlEnumToBool(x: Int): Boolean = {
    x == GL_TRUE
  }

}
