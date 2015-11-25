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

object SubscribeCommand {

  //sub|subscribe|join|listen|view -- lists subscribed channels
  //<channel> -- subscribes to that channel
  def apply(player: Player): Unit ={
    player.sendMessage(Messages("channel.sub.list.header"))
    ClusterPlayer(player.getUniqueId).channelStorage.subscribedChannels
      .foreach(chan => player.sendMessage(Messages("channel.sub.list.item", MsgVar("{NAME}", chan.name))))
  }

  def apply(player: Player, name: String): Unit ={
    val channel = Channel.get(name)
    if(channel.isDefined){
      val stuff = ClusterPlayer(player.getUniqueId).channelStorage
      if(stuff.isSubscribed(channel.get)){
        player.sendMessage(Messages("channel.sub.error.alreadySubscribed", MsgVar("{NAME}", name.toLowerCase)))
      }else{
        if(channel.get.canSubscribe(player)){
          player.sendMessage(Messages("channel.sub.success", MsgVar("{NAME}", name.toLowerCase)))
          stuff.subscribe(channel.get)
        }else player.sendMessage(Messages("channel.sub.error.noPermission", MsgVar("{NAME}", name.toLowerCase)))
      }
    }else player.sendMessage(Messages("channel.sub.error.noExist", MsgVar("{NAME}", name.toLowerCase)))
  }
}
