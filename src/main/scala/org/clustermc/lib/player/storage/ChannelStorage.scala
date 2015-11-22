package org.clustermc.lib.player.storage

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.clustermc.lib.chat.channel.Channel

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
class ChannelStorage(val player: Player) {

    val subscribedChannels = new collection.mutable.TreeSet[Channel]()
    private var _focusedChannel: Option[Channel] = Option.empty

    def isSubscribed(channel: Channel): Boolean = {
        (subscribedChannels.contains(channel) && channel.canSubscribe(player)) ||
            (_focusedChannel.isDefined && _focusedChannel.get.canSubscribe(player))
    }

    def subscribe(cObject: Option[_ >: String with Channel]): Unit = {
        if(cObject.isDefined) {
            cObject.get match {
                case s: String =>
                    val cOption = Channel.get(s)
                    if(cOption.isDefined) subscribe(cOption.get)
                    else player.sendMessage(s"${ChatColor.GOLD }$s ${ChatColor.RED }does not exist.")
                case c: Channel => subscribe(c)
                case _ => player.sendMessage(s"${ChatColor.RED }You cannot subscribe to a channel that doesn't exist.")
            }
        }
    }

    def focus(channel: Channel) = {
        _focusedChannel = Option(channel)
        if(!isSubscribed(channel)) {
            subscribe(channel)
        }
    }

    def subscribe(channel: Channel) = {
        if(!subscribedChannels.contains(channel)) {
            subscribedChannels.add(channel)
        }
        channel.join(player)
    }

    def unsubscribe(cObject: Option[_ >: String with Channel]): Unit = {
        if(cObject.isDefined) {
            cObject.get match {
                case s: String =>
                    val channel = Channel.get(s)
                    if(channel.isDefined) unsubscribe(channel.get)
                    else player.sendMessage(s"${ChatColor.GOLD }$s ${ChatColor.RED }does not exist.")
                case c: Channel => unsubscribe(c)
                case _ => player.sendMessage(s"${ChatColor.RED }You cannot leave to a channel that doesn't exist.")
            }
        }
    }

    def unsubscribe(channel: Channel) = {
        if(subscribedChannels.contains(channel)) {
            subscribedChannels.remove(channel)
            if(subscribedChannels.isEmpty){
                subscribe(Channel.get("general").get)
            }
        }
        if(focusedChannel.name == channel.name) {
            focus(subscribedChannels.head)
        }
    }

    def focusedChannel = {
        if(_focusedChannel.isEmpty) _focusedChannel = Channel.get("general")
        _focusedChannel.get
    }
}
