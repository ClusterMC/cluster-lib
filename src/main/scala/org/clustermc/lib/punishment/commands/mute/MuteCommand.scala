package org.clustermc.lib.punishment.commands.mute

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.{punishmentMutePermMuted, punishmentMutePermMuter}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object MuteCommand extends PunishmentCommand{

  override val minArgLength: Int = 2

  override def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    ppunished.punishments._mute = Option(
      Punishment.create(PunishmentType.MUTE, punisher.getUniqueId, punished.getUniqueId, reason)
        .objectId)
    if(online){
      punished.sendMessage(punishmentMutePermMuted(punisher.getName, reason).get)
    }
    punisher.sendMessage(punishmentMutePermMuter(punished.getName, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.MOD
  override val needsOnline: Boolean = false
  override val color: String = "62CCB1"
}

