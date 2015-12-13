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

object AchievementMsg {
  val achievement = MsgVal("achievement")

  val title = achievement.join("title")
  case class achievementTitleTitle(name: String) extends MsgVal(title.node("title"), MsgVar("name", name))
  case class achievementTitleSubtitle(description: String) extends MsgVal(title.node("subtitle"), MsgVar("description", description))

}
