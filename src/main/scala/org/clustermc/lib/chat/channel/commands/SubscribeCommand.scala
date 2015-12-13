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

object SubscribeCommand {

  //sub|subscribe|join|listen|view -- lists subscribed channels
  //<channel> -- subscribes to that channel
  def apply(player: Player): Unit ={
    player.sendMessage(channelSubListHeader().get)
    ClusterPlayer(player.getUniqueId).channelStorage.subscribedChannels
      .foreach(chan => player.sendMessage(channelSubListItem(chan.name).get))
  }

  def apply(player: Player, name: String): Unit ={
    val channel = Channel.get(name)
    if(channel.isDefined){
      val stuff = ClusterPlayer(player.getUniqueId).channelStorage
      if(stuff.isSubscribed(channel.get)){
        stuff.unsubscribe(channel.get)
        player.sendMessage(channelUnsubSuccess(name.toLowerCase).get)
      }else{
        if(channel.get.canSubscribe(player)){
          player.sendMessage(channelSubSuccess(name.toLowerCase).get)
          stuff.subscribe(channel.get)
        }else player.sendMessage(channelNoPermission(name.toLowerCase).get)
      }
    }else player.sendMessage(channelNoExist(name.toLowerCase).get)
  }
}
