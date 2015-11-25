package org.clustermc.lib.chat.channel.commands

import org.bukkit.entity.Player
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.player.storage.ClusterPlayer
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

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
      player.sendMessage(Messages("channel.focus.current",
        MsgVar("{NAME}", ClusterPlayer(player.getUniqueId).channelStorage.focusedChannel.name)))
  }

  def apply(player: Player, name: String): Unit ={
    val channel = Channel.get(name)
    if(channel.isDefined){
      val storage = ClusterPlayer(player.getUniqueId).channelStorage
      if(storage.focusedChannel.name == name.toLowerCase){
        player.sendMessage(Messages("channel.focus.error.alreadyFocused", MsgVar("{NAME}", name.toLowerCase)))
      }else{
        if(channel.get.canFocus(player)){
          player.sendMessage(Messages("channel.focus.success", MsgVar("{NAME}", name.toLowerCase)))
          storage.focus(channel.get)
        }else player.sendMessage(Messages("channel.focus.error.noPermission", MsgVar("{NAME}", name.toLowerCase)))
      }
    }else player.sendMessage(Messages("channel.focus.error.noExist", MsgVar("{NAME}", name.toLowerCase)))
  }

}
