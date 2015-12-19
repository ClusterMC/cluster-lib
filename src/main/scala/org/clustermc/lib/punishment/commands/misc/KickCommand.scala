package org.clustermc.lib.punishment.commands.misc

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.{punishmentKickKicked, punishmentKickKicker}

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

  override def punish(ppunished: LibPlayer, pplayer: LibPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    Punishment.create(PunishmentType.KICK, punisher.getUniqueId, punished.getUniqueId, reason)
    punished.kickPlayer(punishmentKickKicked(punisher.getName, reason).get)
    punisher.sendMessage(punishmentKickKicker(punished.getName, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.MOD
  override val needsOnline: Boolean = true
  override val color: String = "BDC459"
}
