package org.theclustermc.lib.utils

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object CaseInsensitiveOrdered extends Ordering[String] {
  def compare(x: String, y: String): Int = x.compareToIgnoreCase(y)
}
