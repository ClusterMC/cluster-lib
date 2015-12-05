package org.clustermc.lib.player.storage

import java.util.UUID

import org.bukkit.Bukkit
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.utils.messages.Messages

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
//TODO: Fix the generic typing
class ChannelStorage(uuid: UUID) {
    lazy val player = Bukkit.getPlayer(uuid)


    val subscribedChannels = new collection.mutable.TreeSet[Channel]()
    private var _focusedChannel: Option[Channel] = Option.empty

    def isSubscribed(channel: Channel): Boolean = {
        (subscribedChannels.contains(channel) && channel.canSubscribe(player)) ||
            (_focusedChannel.isDefined && _focusedChannel.get.canSubscribe(player))
    }

    def focus(channel: Channel) = {
        _focusedChannel = Option(channel)
        if(!isSubscribed(channel)) {
            subscribe(channel)
        }
    }

    def init(): Unit ={
        _focusedChannel = Channel.get("general")
        subscribedChannels.add(_focusedChannel.get)
        _focusedChannel.get.fjoin(uuid)
    }

    def subscribe(channel: Channel) = {
        if(!subscribedChannels.contains(channel)) {
            subscribedChannels.add(channel)
        }
        channel.join(player)
    }

    def unsubscribe(channel: Channel): Unit = {
        if(subscribedChannels.contains(channel)) {
            subscribedChannels.remove(channel)
            if(subscribedChannels.isEmpty){
                subscribe(Channel.get("general").get)
            }
        }
        if(focusedChannel.name == channel.name) {
            if(subscribedChannels.isEmpty){
                player.sendMessage(Messages("channel.error.cantLeaveFocusWhenNoSub"))
                return
            }
            focus(subscribedChannels.head)
        }
    }

    def focusedChannel = {
        if(_focusedChannel.isEmpty) _focusedChannel = Channel.get("general")
        _focusedChannel.get
    }
}
