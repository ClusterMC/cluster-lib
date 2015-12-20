package org.clustermc.lib.chat.channel

import java.util.UUID

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.utils.CustomConfig
import org.clustermc.lib.utils.messages.vals.ChannelMsg.{channelAlertFooter, channelAlertHeader, channelAlertMessage}

import scala.collection.JavaConverters._

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class Channel(val name: String, val format: String, val sendPerm: String, val receivePerm: String)
    extends Ordered[Channel] {

    @transient val members = new collection.mutable.LinkedHashSet[UUID]()

    def canFocus(player: Player) = canSend(player)
    def canSend(player: Player): Boolean = LibPlayer(player.getUniqueId).hasRank(sendPerm)

    def canSubscribe(player: Player) = canReceive(player)
    def canReceive(player: Player): Boolean = LibPlayer(player.getUniqueId).hasRank(receivePerm)

    def join(player: Player) = {
        if(canSubscribe(player) && !members.contains(player.getUniqueId))
            members.add(player.getUniqueId)
    }

    def fjoin(uuid: UUID): Unit ={
        if(!members.contains(uuid)){
            members.add(uuid)
        }
    }

    def leave(uuid: UUID) = {
        if(members.contains(uuid)) members.remove(uuid)
    }

    def forceSend(message: String) = {
        val iter = members.iterator
        while(iter.hasNext){
            val uuid = iter.next()
            val p = Bukkit.getPlayer(uuid)
            if(p != null && p.isOnline)
                p.sendMessage(message)
            else
                members.remove(uuid)
        }
    }

    override def compare(that: Channel): Int = name.compare(that.name)
}

object Channel {
    val channels = new collection.mutable.HashMap[String, Channel]()

    //Load the channels, this is a constructor method
    loadChannels()

    def get(name: String): Option[Channel] = {
        val cOption = channels.get(name.toLowerCase)
        if(cOption.isDefined) cOption
        else None
    }

    def serverAlert(message: String, receivedMessage: Boolean = false): Unit ={
        if(receivedMessage){
            //todo
        }
        Bukkit.getServer.broadcastMessage(channelAlertHeader().get)
        Bukkit.getServer.broadcastMessage(channelAlertMessage(message).get)
        Bukkit.getServer.broadcastMessage(channelAlertFooter().get)
    }

    //TODO allowed groups
    def loadChannels(): Unit = {
        val config = new CustomConfig(ClusterLib.instance.getDataFolder, "channels")
                            .getConfig.getConfigurationSection("channels")
        config.getKeys(false).asScala.foreach{key =>
            val section = config.getConfigurationSection(key)
            register(new Channel(
                key.toLowerCase,
                section.getString("format"),
                section.getString("send"),
                section.getString("receive"))
                )
        }
        channels.keySet.foreach(key => println("done + " + key))
    }

    def register(c: Channel) = channels.put(c.name.toLowerCase, c)

    def unregister(c: Channel): Unit = this.unregister(c.name.toLowerCase)

    def unregister(name: String) = if(channels.contains(name.toLowerCase)) channels.remove(name.toLowerCase)
}
