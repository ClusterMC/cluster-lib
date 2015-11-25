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

class UnbanCommand extends PunishmentCommand{

  override val minArgLength: Int = 2

  override def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    Punishment.create(PunishmentType.UNBAN, punished.getUniqueId, punished.getUniqueId, reason)
    ppunished.punishments._ban = None
    punisher.sendMessage(Messages(msgPrefix + "unBan",
      MsgVar("{REASON}", reason),
      MsgVar("{PUNISHED}", ppunished.latestName)))
  }

  override val permRequired: PermissionRank = PermissionRank.ADMIN
  override val name: String = "ban"
  override val needsOnline: Boolean = false
}

