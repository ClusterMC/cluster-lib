package org.clustermc.lib.punishment.commands.misc

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.TitleAPI
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.{punishmentWarnSubtitle, punishmentWarnTitle, punishmentWarnWarner}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object WarnCommand extends PunishmentCommand{

  override val minArgLength: Int = 2

  override def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    Punishment.create(PunishmentType.WARN, punisher.getUniqueId, punished.getUniqueId, reason)
    val title = punishmentWarnTitle(punisher.getName, reason).get
    val subtitle = punishmentWarnSubtitle(punished.getName, reason).get
    TitleAPI.sendTitle(punished, int2Integer(5), int2Integer(60), int2Integer(5), title, subtitle)
    punisher.sendMessage(punishmentWarnWarner(punished.getName, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.MOD
  override val needsOnline: Boolean = true
  override val color: String = "E39568"
}