package org.clustermc.lib.chat.channel.commands

import org.bukkit.entity.Player
import org.clustermc.lib.utils.StringUtil

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object ChannelHelpCommand {

  val help = StringUtil.colorArray(Array(
    "&7===&b&lChannel Help&7===",
    "  &bhelp&7|&bh&7|&b? &7-- displays the help commands",
    "    &7eg. &b/channel help",
    "&7===&b&lChannel Sub&7===",
    "  &bsub&7|&bjoin&7|&bview &7-- lists subscribed channels",
    "    &7eg. &b/channel sub &7(&cname&7) -- joins selected channel",
    "&7===&b&lChannel Focus&7===",
    "  &bfocus&7|&bf&7|&btalk&7|&bt &7-- lists the name of focused channels you're currently in",
    "    &7eg. &b/channel focus &7(&cname&7) -- subscribes and focuses on selected channel",
    "&7===&b&lChannel Leave&7===",
    "  &bleave&7|&bexit&7|&bquit &7-- leaves currently focused channel",
    "    &7eg. &b/channel leave &7(&cname&7) -- leaves currently focused channel",
    "&7===&b&lChannel Quick-Send&7===",
    "  &7<&cchannel&7> <&cmessage&7> -- sends a quick message to a channel",
    "    &7eg. &b/channel general &7(&cmessage&7) -- quickly sends a message to a channel"))

  def apply(sender: Player) = sender.sendMessage(help)
}
