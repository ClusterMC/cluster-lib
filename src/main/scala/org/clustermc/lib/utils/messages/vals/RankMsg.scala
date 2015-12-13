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

object RankMsg {
  val rank = MsgVal("rank")

  case class rankSuccess(player: String) extends MsgVal(error.node("success"), MsgVar("player", player))

  val error = rank.join("error")
  case class rankErrorInvalidRank(input: String) extends MsgVal(error.node("invalidRank"), MsgVar("input", input))
  case class rankErrorArgs() extends MsgVal(error.node("wrongArgs"))

}
