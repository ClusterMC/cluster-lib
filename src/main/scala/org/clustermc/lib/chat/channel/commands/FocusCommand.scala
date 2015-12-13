package org.clustermc.lib.chat.channel.commands

import org.bukkit.entity.Player
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.utils.messages.vals.ChannelMsg._

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object FocusCommand {

  //focus|f|talk|t -- lists name of channel youre talking in
  //<channel> -- subscribes to and focuses on channel
  def apply(player: Player): Unit ={
    player.sendMessage(channelFocusCurrent(ClusterPlayer(player.getUniqueId).channelStorage.focusedChannel.name).get)
  }

  def apply(player: Player, name: String): Unit ={
    val channel = Channel.get(name)
    if(channel.isDefined){
      val storage = ClusterPlayer(player.getUniqueId).channelStorage
      if(storage.focusedChannel.name == name.toLowerCase){
        player.sendMessage(channelFocusErrorAlreadyFocused(name.toLowerCase).get)
      }else{
        if(channel.get.canFocus(player)){
          player.sendMessage(channelFocusSuccess(name.toLowerCase).get)
          storage.focus(channel.get)
        }else player.sendMessage(channelNoPermission(name.toLowerCase).get)
      }
    }else player.sendMessage(channelNoExist(name.toLowerCase).get)
  }

}
