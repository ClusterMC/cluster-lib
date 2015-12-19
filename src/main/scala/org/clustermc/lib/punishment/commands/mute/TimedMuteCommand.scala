package org.clustermc.lib.punishment.commands.mute

import java.time.Duration

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.TimeParser
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.{punishmentErrorInvalidDuration, punishmentMuteTempMuted, punishmentMuteTempMuter}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object TimedMuteCommand extends PunishmentCommand{

  override val minArgLength: Int = 3

  override def punish(ppunished: LibPlayer, pplayer: LibPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    val duration = TimeParser(args(1).toLowerCase)
    if(duration == Duration.ZERO){
      punisher.sendMessage(punishmentErrorInvalidDuration(args(1).toLowerCase).get)
      return
    }
    ppunished.punishments._mute = Option(
      Punishment.create(PunishmentType.TEMPMUTE, punisher.getUniqueId, punished.getUniqueId, reason, duration)
        .objectId)
    if(online){
      punished.sendMessage(punishmentMuteTempMuted(punisher.getName, args(1).toLowerCase, reason).get)
    }
    punisher.sendMessage(punishmentMuteTempMuter(punished.getName, args(1).toLowerCase, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.MOD
  override val needsOnline: Boolean = false
  override val color: String = "C7FFF1"
}
