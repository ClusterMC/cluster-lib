package org.clustermc.lib.player

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class DV[T](val default: T) {
  var value: T = default
  def isDefault: Boolean = default == value
}
object DV{
  def apply[T](default: T): DV[T] = new DV[T](default)
}
