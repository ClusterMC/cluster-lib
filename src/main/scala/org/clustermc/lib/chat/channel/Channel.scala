package org.clustermc.lib.chat.channel

import java.util.UUID

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.clustermc.lib.player.storage.PlayerCoordinator
import org.clustermc.lib.utils.{CustomConfig, StringUtil}
import org.clustermc.lib.{ClusterLib, PermGroup}

import scala.collection.JavaConverters._

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

//TODO: Fix

class Channel(val name: String, val format: String, val sendPerm: PermGroup, val receivePerm: PermGroup)
    extends Ordered[Channel] {

    @transient val members = new collection.mutable.LinkedHashSet[UUID]()

    def canFocus(player: Player) = canSend(player)
    def canSend(player: Player): Boolean = PlayerCoordinator(player.getUniqueId).hasRank(sendPerm)

    def canSubscribe(player: Player) = canReceive(player)
    def canReceive(player: Player): Boolean = PlayerCoordinator(player.getUniqueId).hasRank(receivePerm)

    def join(player: Player) = {
        if(canSubscribe(player) && !members.contains(player.getUniqueId))
            members.add(player.getUniqueId)
    }

    def leave(uuid: UUID) = {
        if(members.contains(uuid)) members.remove(uuid)
    }

    def commandMessage(player: Player, message: String) = {
        members.foreach((uuid: UUID) => {
            val p = Bukkit.getPlayer(uuid)
            if(p != null && p.isOnline) {
                p.sendMessage(StringUtil.colorString(message))
            }
        })
    }

    def eventMessage(event: AsyncPlayerChatEvent) = {
        event.getRecipients.clear()
        event.getRecipients.addAll(members.flatMap((u: UUID) =>
            Option.apply(Bukkit.getPlayer(u)) match {
                case p: Some[Player] if p.get.isOnline => p
                case _ => None
            }).asJava)
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

    def serverAlert(message: String): Unit ={
        //TODO ADD SERVER-WIDE ALERTS
    }

    def networkAlert(message: String): Unit ={
        //TODO ADD NETWORK-WIDE ALERTS
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
                PermGroup.valueOf(section.getString("send")),
                PermGroup.valueOf(section.getString("receive")))
                )
        }
    }

    def register(c: Channel) = channels.put(c.name.toLowerCase, c)

    def unregister(c: Channel): Unit = this.unregister(c.name.toLowerCase)

    def unregister(name: String) = if(channels.contains(name.toLowerCase)) channels.remove(name.toLowerCase)
}
