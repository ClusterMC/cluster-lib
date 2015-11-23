package org.clustermc.lib.chat.channel.commands

import org.bukkit.entity.Player
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.player.storage.PlayerCoordinator
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object LeaveCommand {

  //leave|exit|x|quit|q|ex -- leaves currently focused channel and switches to global
  //<channel> -- leaves channel
  def apply(player: Player): Unit ={
    val storage = PlayerCoordinator(player.getUniqueId).channelStorage
    if(storage.focusedChannel.name.equalsIgnoreCase("general")){
      player.sendMessage(Messages("channel.leave.error.cantLeaveGeneralWhenFocused"))
    }else{
      val left = storage.focusedChannel.name
      storage.unsubscribe(storage.focusedChannel)
      player.sendMessage(Messages("channel.leave.success",
        MsgVar("{LEFT}", left), MsgVar("{NEW}", storage.focusedChannel.name)))
    }
  }

  def apply(player: Player, name: String): Unit ={
    val channel = Channel.get(name)
    if(channel.isDefined) {
      val storage = PlayerCoordinator(player.getUniqueId).channelStorage
      if(storage.isSubscribed(channel.get)){
        storage.unsubscribe(channel.get)
        player.sendMessage(Messages("channel.leave.success",
          MsgVar("{LEFT}", channel.get.name),
          MsgVar("{NEW}", storage.focusedChannel.name)))
      }else player.sendMessage(Messages("channel.leave.error.notSubscribed", MsgVar("{NAME}", name.toLowerCase)))
    }else player.sendMessage(Messages("channel.focus.error.noExist", MsgVar("{NAME}", name.toLowerCase)))
  }

}
