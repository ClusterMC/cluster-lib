package org.clustermc.lib.chat.channel.commands

import org.bukkit.entity.Player
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.chat.event.CPlayerChatEvent
import org.clustermc.lib.utils.messages.vals.ChannelMsg.channelNoExist

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object SendCommand {
  def apply(sender: Player, name: String, words: Array[String]): Unit = {
    val sentence = words.mkString(" ")
    val channel = Channel.get(name)
    if(channel.isDefined){
      CPlayerChatEvent.processChat(sender, sentence, channel.get)
    }else sender.sendMessage(channelNoExist(name.toLowerCase).get)
  }

}
