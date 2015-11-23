package org.clustermc.lib.chat.channel.commands

import org.bukkit.entity.Player
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.player.storage.PlayerCoordinator
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

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
    val pplayer = PlayerCoordinator(sender.getUniqueId)
    if(!pplayer.muted){
      if(channel.isDefined){
        if(channel.get.canSend(sender)){
          //TODO Send message to all with formatting
        }else sender.sendMessage(Messages("channel.shortcut.cantSend", MsgVar("{TIME}", 5)))
      }else sender.sendMessage(Messages("channel.shortcut.noExist", MsgVar("{NAME}", name.toLowerCase)))
    }else sender.sendMessage(Messages("punishment.youremuted", MsgVar("{TIME}", Punishment.timeLeft(pplayer.punishments._mute.get))))
  }

}
