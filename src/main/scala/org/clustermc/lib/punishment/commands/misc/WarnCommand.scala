package org.clustermc.lib.punishment.commands.misc

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.punishment.PunishmentManager
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.punishmentWarnWarner

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

  override def punish(punisher: Player, punished: Player, reason: String, duration: String): Unit = {
    PunishmentManager.warn(punisher.getName, punisher.getUniqueId, punished.getUniqueId, reason)
    punisher.sendMessage(punishmentWarnWarner(punished.getName, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.MOD
  override val needsOnline: Boolean = true
  override val color: String = "E39568"
}