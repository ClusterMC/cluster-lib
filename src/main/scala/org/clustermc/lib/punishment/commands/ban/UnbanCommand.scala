package org.clustermc.lib.punishment.commands.ban

import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.commands.PunishmentCommand
import org.clustermc.lib.punishment.data.Punishment
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

  override def punish(ppunished: LibPlayer, pplayer: LibPlayer, punisher: Player, punished: Player, reason: String, online: Boolean, args: Array[String]): Unit = {
    Punishment.create(PunishmentType.UNBAN, punisher.getUniqueId, punished.getUniqueId, reason)
    ppunished.punishments._ban = None
    if(!online){
      LibPlayer.unload(ppunished.itemId)
    }
    punisher.sendMessage(punishmentBanUnban(ppunished.latestName, reason).get)
  }

  override val permRequired: PermissionRank = PermissionRank.ADMIN
  override val needsOnline: Boolean = false
  override val color: String = "FF7A73"
}

