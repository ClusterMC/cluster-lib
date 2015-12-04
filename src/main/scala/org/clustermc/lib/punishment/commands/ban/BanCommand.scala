package org.clustermc.lib.punishment.commands.ban

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object BanCommand extends PunishmentCommand{

  override val minArgLength: Int = 2

  override def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    ppunished.punishments._ban = Option(
      Punishment.create(PunishmentType.BAN, punished.getUniqueId, punished.getUniqueId, reason)
        .objectId)
    if(online){
      punished.kickPlayer(Messages(msgPrefix + "permBanned",
        MsgVar("{PUNISHER}", punisher.getName),
        MsgVar("{REASON}", reason)))
    }else ClusterPlayer.unload(ppunished.itemId)

    punisher.sendMessage(Messages(msgPrefix + "permBanner",
      MsgVar("{REASON}", reason),
      MsgVar("{PUNISHED}", ppunished.latestName)))
  }

  override val permRequired: PermissionRank = PermissionRank.NETADMIN
  override val name: String = "ban"
  override val needsOnline: Boolean = false
  override val color: String = "6A332C"
}
