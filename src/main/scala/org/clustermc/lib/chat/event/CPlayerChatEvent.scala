package org.clustermc.lib.chat.event

import org.bukkit.entity.Player
import org.bukkit.event.{Event, HandlerList}
import org.clustermc.lib.chat.ColorFilter
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.StringUtil
import org.clustermc.lib.utils.messages.vals.ChannelMsg.{channelErrorNoFocusPermAnymore, channelErrorNoSubPermAnymore, channelNoPermission}
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.punishmentYouAreMuted

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class CPlayerChatEvent extends Event{
  override def getHandlers: HandlerList = CPlayerChatEvent.handlers
  //TODO make useful
}
object CPlayerChatEvent{
  private val handlers: HandlerList = new HandlerList()

  def processChat(player: Player, message: String, channel: Channel): Boolean ={
    val pplayer = ClusterPlayer(player.getUniqueId)
    if(pplayer.muted){
      player.sendMessage(punishmentYouAreMuted(Punishment.timeLeft(pplayer.punishments._mute.get).get.toString).get)
      return false
    }

    if(!channel.canSend(player)) {
      player.sendMessage(channelNoPermission(channel.name).get)
      player.sendMessage(channelErrorNoFocusPermAnymore(channel.name).get)
      if(!channel.canSubscribe(player)){
        player.sendMessage(channelErrorNoSubPermAnymore(channel.name).get)
        pplayer.channelStorage.unsubscribe(channel)
      }
      pplayer.channelStorage.focus(Channel.get("general").get)
      return false
    }

    val groupData = pplayer.chatRank

    channel.forceSend(StringUtil.colorString(channel.format
      .replace("{PLAYER}", player.getName)
      .replace("{PLAYER_COLOR}", groupData.player)
      .replace("{PREFIX}", groupData.prefix)
      .replace("{SUFFIX}", groupData.suffix)
      .replace("{MESSAGE_COLOR}", groupData.chat)
      .replace("{MESSAGE}", ColorFilter.filter(pplayer, message))))

    return true
  }

  def processChat(player: Player, message: String): Boolean ={
    processChat(player, message, ClusterPlayer(player.getUniqueId).channelStorage.focusedChannel)
  }

}