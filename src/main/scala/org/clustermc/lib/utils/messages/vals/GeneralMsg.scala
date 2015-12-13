package org.clustermc.lib.utils.messages.vals

import org.clustermc.lib.utils.messages.{MsgVal, MsgVar}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object GeneralMsg {
  val general = MsgVal("general")

  case class generalNoPermission() extends MsgVal(general.node("noPermission"))
  case class generalNotOnline() extends MsgVal(general.node("notOnline"))
  case class generalPlayerNoExist(input: String) extends MsgVal(general.node("playerNoExist"), MsgVar("input", input))
}
