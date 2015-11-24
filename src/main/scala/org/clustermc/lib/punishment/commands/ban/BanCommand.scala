package org.clustermc.lib.punishment.commands.ban

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object BanCommand {

  /**
   *           punished.kickPlayer(Messages("punishment.ban.gotPermBanned",
            MsgVar("{PUNISHER}", context.sender.getName),
            MsgVar("{REASON}", context.args.drop(1).mkString(" "))))
   */
  /**
   *         ppunished.punishments._ban = Option(
          Punishment.create(
            PunishmentType.BAN, context.sender.getUniqueId, ppunished.itemId, reason
          ).objectId)
        context.sender.sendMessage(Messages("punishment.ban.youPermBanned",
          MsgVar("{REASON}", reason),
          MsgVar("{PUNISHED}", ppunished.latestName)))
   */
}
