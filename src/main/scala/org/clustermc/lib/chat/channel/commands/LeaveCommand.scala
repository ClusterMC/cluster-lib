package org.clustermc.lib.chat.channel.commands

import org.bukkit.entity.Player
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.utils.messages.vals.ChannelMsg.{channelCantLeaveFocusWhenNoSub, channelLeaveErrorNotSubscribed, channelLeaveSuccess, channelNoExist}

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
    val storage = ClusterPlayer(player.getUniqueId).channelStorage
    if(storage.focusedChannel.name.equalsIgnoreCase("general")){
      player.sendMessage(channelCantLeaveFocusWhenNoSub().get)
    }else{
      val left = storage.focusedChannel.name
      storage.unsubscribe(storage.focusedChannel)
      player.sendMessage(channelLeaveSuccess(left, storage.focusedChannel.name).get)
    }
  }

  def apply(player: Player, name: String): Unit ={
    val channel = Channel.get(name)
    if(channel.isDefined) {
      val storage = ClusterPlayer(player.getUniqueId).channelStorage
      if(storage.isSubscribed(channel.get)){
        storage.unsubscribe(channel.get)
        player.sendMessage(channelLeaveSuccess(channel.get.name, storage.focusedChannel.name).get)
      }else player.sendMessage(channelLeaveErrorNotSubscribed(channel.get.name).get)
    }else player.sendMessage(channelNoExist(name.toLowerCase).get)
  }

}
