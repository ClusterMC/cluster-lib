package org.clustermc.lib.punishment.commands.mute

import java.time.Duration

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
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

class TimedMuteCommand extends PunishmentCommand{

  override val minArgLength: Int = 3

  override def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    val duration = TimeParser(args(1).toLowerCase)
    if(duration == Duration.ZERO){
      punisher.sendMessage(Messages(msgPrefix + "invalidDuration", MsgVar("{ATTEMPT}", args(1).toLowerCase)))
      return
    }
    ppunished.punishments._mute = Option(
      Punishment.create(PunishmentType.TEMPMUTE, punished.getUniqueId, punished.getUniqueId, reason, duration)
        .objectId)
    if(online){
      punished.sendMessage(Messages(msgPrefix + "gotTempMuted",
        MsgVar("{PUNISHER}", punisher.getName),
        MsgVar("{REASON}", reason)))
    }
    punisher.sendMessage(Messages(msgPrefix + "youTempMuted",
      MsgVar("{REASON}", reason),
      MsgVar("{PUNISHED}", ppunished.latestName)))
  }

  override val permRequired: PermissionRank = PermissionRank.MOD
  override val name: String = "mute"
  override val needsOnline: Boolean = false
}
