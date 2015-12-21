package org.clustermc.lib.punishment.commands.ban

import java.time.Duration

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.punishment.PunishmentManager
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.utils.TimeParser
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.{punishmentBanTempBanner, punishmentErrorInvalidDuration}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object TimedBanCommand extends PunishmentCommand{

  override val minArgLength: Int = 3

  override def punish(punisher: Player, punished: Player, reason: String, duration: String): Unit = {
    val time = TimeParser(duration)
    if(time == Duration.ZERO){
      punisher.sendMessage(punishmentErrorInvalidDuration(duration).get)
      return
    }
    PunishmentManager.tempban(time, punisher.getName, punisher.getUniqueId, punished.getUniqueId, reason)
    punisher.sendMessage(punishmentBanTempBanner(punisher.getName, duration, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.ADMIN
  override val needsOnline: Boolean = false
  override val color: String = "9F5E51"
}
