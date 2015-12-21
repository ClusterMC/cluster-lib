package org.clustermc.lib.punishment.commands.ban

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.punishment.PunishmentManager
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.punishmentBanUnban

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object UnbanCommand extends PunishmentCommand{

  override val minArgLength: Int = 2

  override def punish(punisher: Player, punished: Player, reason: String, duration: String): Unit = {
    PunishmentManager.unban(punisher.getName, punisher.getUniqueId, punished.getUniqueId, reason)
    punisher.sendMessage(punishmentBanUnban(punished.getName, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.ADMIN
  override val needsOnline: Boolean = false
  override val color: String = "FF7A73"
}

