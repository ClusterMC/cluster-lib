package org.clustermc.lib.punishment.commands.misc

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

object KickCommand extends PunishmentCommand{

  override val minArgLength: Int = 2

  override def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    Punishment.create(PunishmentType.KICK, punished.getUniqueId, punished.getUniqueId, reason)
    punished.kickPlayer(Messages(msgPrefix + "kicked",
        MsgVar("{PUNISHER}", punisher.getName),
        MsgVar("{REASON}", reason)))
    punisher.sendMessage(Messages(msgPrefix + "kicker",
      MsgVar("{REASON}", reason),
      MsgVar("{PUNISHED}", ppunished.latestName)))
  }

  override val permRequired: PermissionRank = PermissionRank.MOD
  override val name: String = "kick"
  override val needsOnline: Boolean = true
}
