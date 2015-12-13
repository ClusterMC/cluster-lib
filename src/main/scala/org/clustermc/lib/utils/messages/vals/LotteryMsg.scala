package org.clustermc.lib.utils.messages.vals

import org.clustermc.lib.utils.messages.MsgVal

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object LotteryMsg {
  val lottery = MsgVal("lottery")

  val error = lottery.join("error")
  case class lotteryNotEnough() extends MsgVal(error.node("notEnough"))
  case class lotteryOnlyNoneStaff() extends MsgVal(error.node("onlyNonStaff"))
  case class lotteryCantEnterZero() extends MsgVal(error.node("cantEnterZero"))
  case class lotteryAlreadyEnrolled() extends MsgVal(error.node("alreadyEnrolled"))

  val lotteryShard = lottery.join("shard")
  case class lotteryShardHeader() extends MsgVal(lotteryShard.node("header"))

  val lotteryCluster = lottery.join("cluster")
  case class lotteryClusterHeader() extends MsgVal(lotteryCluster.node("header"))
}
