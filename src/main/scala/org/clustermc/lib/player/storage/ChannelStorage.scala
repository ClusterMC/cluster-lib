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
        (subscribedChannels.contains(channel) && channel.canReceive(player)) ||
            (_focusedChannel.isDefined && _focusedChannel.get.canReceive(player))
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

    def subscribe(channel: Channel) = {
        if(!subscribedChannels.contains(channel)) {
            subscribedChannels.add(channel)
        }
        channel.join(player)
        setFocusedChannel(channel)
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
        }
        channel.leave(player.getUniqueId)
        if(subscribedChannels.isEmpty) {
            setFocusedChannel(None)
        } else {
            setFocusedChannel(subscribedChannels.head)
        }
    }

    def setFocusedChannel(channel: Channel): Unit = {
        setFocusedChannel(Option.apply(channel))
    }

    def setFocusedChannel(channel: Option[Channel]): Unit = {
        if(channel.isDefined) {
            _focusedChannel = channel
            if(!isSubscribed(channel.get)) {
                subscribe(channel.get)
            }
        } else {
            _focusedChannel = Channel.get("general")
        }
    }

    def displayChannel() = {

    }

    def focusedChannel = _focusedChannel
}
