package org.clustermc.lib.punishment.commands.ban

import java.time.Duration

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.TimeParser
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.{punishmentBanTempBanned, punishmentBanTempBanner, punishmentErrorInvalidDuration}

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

  override def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    val duration = TimeParser(args(1).toLowerCase)
    if(duration == Duration.ZERO){
      punisher.sendMessage(punishmentErrorInvalidDuration(args(1).toLowerCase).get)
      return
    }
    ppunished.punishments._ban = Option(
      Punishment.create(PunishmentType.TEMPBAN, punisher.getUniqueId, punished.getUniqueId, reason, duration)
        .objectId)

    if(online){
      punished.kickPlayer(punishmentBanTempBanned(punisher.getName, args(1).toLowerCase, reason).get)
    }else ClusterPlayer.unload(ppunished.itemId)

    punisher.sendMessage(punishmentBanTempBanner(punisher.getName, args(1).toLowerCase, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.ADMIN
  override val needsOnline: Boolean = false
  override val color: String = "9F5E51"
}
