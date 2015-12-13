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

object EconMsg {
  val econ = MsgVal("econ")

  val success = econ.join("success")
  case class econSuccess(command: String, player: String, amount: Int, balance: String)
    extends MsgVal(success.node(command), MsgVar("player", player), MsgVar("amount", amount), MsgVar("balance", balance))

  val error = econ.join("error")
  case class econErrorArgs() extends MsgVal(error.node("wrongArgs"))
  case class econErrorInvalidCurrency() extends MsgVal(error.node("invalidCurrency"))
  case class econErrorInvalidAmount() extends MsgVal(error.node("invalidAmount"))
}
