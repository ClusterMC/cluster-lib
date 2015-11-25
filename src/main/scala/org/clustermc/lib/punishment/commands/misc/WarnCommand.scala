package org.clustermc.lib.punishment.commands.misc

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.TitleAPI
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class WarnCommand extends PunishmentCommand{

  override val minArgLength: Int = 2

  override def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    Punishment.create(PunishmentType.WARN, punished.getUniqueId, punished.getUniqueId, reason)
    if(online){
      val title = Messages(msgPrefix + "warned.title",
        MsgVar("{PUNISHER}", punisher.getName),
        MsgVar("{REASON}", reason))
      val subtitle = Messages(msgPrefix + "warned.subtitle",
        MsgVar("{PUNISHER}", punisher.getName),
        MsgVar("{REASON}", reason))

      TitleAPI.sendTitle(punished, int2Integer(5), int2Integer(60), int2Integer(5), title, subtitle)
    }
    punisher.sendMessage(Messages(msgPrefix + "warner",
      MsgVar("{REASON}", reason),
      MsgVar("{PUNISHED}", ppunished.latestName)))
  }

  override val permRequired: PermissionRank = PermissionRank.MOD
  override val name: String = "warn"
  override val needsOnline: Boolean = true
}