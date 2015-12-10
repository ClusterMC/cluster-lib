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
    "&f=====&b&lChannel Subscribing&f=====",
    "  &f/ch &bsub &7-- lists subscribed channels",
    "  &f/ch &fsub &f(&bchannel&f)&7 -- join &bchannel&7 as a listener",
    "&f=====&b&lChannel Focus&f=====",
    "  &f/ch &bf &7-- lists the name of focused channels you're currently in",
    "  &f/ch &bf &f(&bchannel&f)&7 -- join &bchannel&7 as a speaker",
    "&f=====&b&lChannel Leave&f=====",
    "  &f/ch &bquit &7-- leaves currently focused channel",
    "  &f/ch &bquit &f(&bchannel&f)&7 -- leaves currently focused channel",
    "&f=====&b&lChannel Quick-Send&f=====",
    "  &f/ch &f(&bchannel&f) &f(&bmessage&f) -- sends &bmessage &7to &bchannel"))

  def apply(sender: Player) = sender.sendMessage(help)
}
