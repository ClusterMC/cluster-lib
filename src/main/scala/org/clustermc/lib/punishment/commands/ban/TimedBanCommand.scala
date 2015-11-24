package org.clustermc.lib.punishment.commands.ban

import java.time.Duration

import org.bukkit.entity.Player
import org.clustermc.lib.PermGroup
import org.clustermc.lib.player.storage.ClusterPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.TimeParser
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

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
      punisher.sendMessage(Messages(msgPrefix + "invalidDuration", MsgVar("{ATTEMPT}", args(1).toLowerCase)))
      return
    }
    ppunished.punishments._ban = Option(
      Punishment.create(PunishmentType.TEMPBAN, punished.getUniqueId, punished.getUniqueId, reason, duration)
        .objectId)
    if(online){
      punished.kickPlayer(Messages(msgPrefix + "gotTempBanned",
        MsgVar("{PUNISHER}", punisher.getName),
        MsgVar("{REASON}", reason)))
    }
    punisher.sendMessage(Messages(msgPrefix + "youTempBanned",
      MsgVar("{REASON}", reason),
      MsgVar("{PUNISHED}", ppunished.latestName)))
  }

  override val permRequired: PermGroup = PermGroup.ADMIN
  override val name: String = "tban"
  override val needsOnline: Boolean = false
}
