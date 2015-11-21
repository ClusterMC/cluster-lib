package org.clustermc.lib.utils.messages

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class MsgVar(val identifier: String, val variable: Any) {
  def replace(input: String) = input.replace(identifier, String.valueOf(variable))
}
object MsgVar{
  def apply(identifier: String, variable: Any): MsgVar = new MsgVar(identifier, variable)
}
