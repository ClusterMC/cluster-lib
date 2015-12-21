package org.clustermc.lib.player.event

import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.event.player.{AsyncPlayerPreLoginEvent, PlayerKickEvent, PlayerLoginEvent, PlayerQuitEvent}
import org.bukkit.event.{EventHandler, EventPriority, Listener}
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.punishment.data.Punishment

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class PlayerIO extends Listener {

  Bukkit.getServer.getPluginManager.registerEvents(this, ClusterLib.instance)

  @EventHandler(priority = EventPriority.LOWEST)
  def prelogin(event: AsyncPlayerPreLoginEvent): Unit ={
    val player = LibPlayer(event.getUniqueId)
    if(player.banned){
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Punishment.reason(player.punishments._ban.get))
      LibPlayer.unload(event.getUniqueId)
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  def login(event: PlayerLoginEvent): Unit = {
    if(!LibPlayer.loaded(event.getPlayer.getUniqueId) && !event.getResult.toString.contains("KICK")){
      val p = LibPlayer(event.getPlayer.getUniqueId)
      p.latestName = event.getPlayer.getName
      ClusterLib.instance.database.getDatabase.getCollection("online").insertOne(
        new Document().append("uuid", p.itemId.toString).append("name", event.getPlayer.getName).append("server", ClusterLib.instance.serverName))
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  def kicked(event: PlayerKickEvent): Unit = {
    LibPlayer(event.getPlayer.getUniqueId).latestName = event.getPlayer.getName
    LibPlayer.unload(event.getPlayer.getUniqueId)
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  def disconnected(event: PlayerQuitEvent): Unit = {
    LibPlayer(event.getPlayer.getUniqueId).latestName = event.getPlayer.getName
    LibPlayer.unload(event.getPlayer.getUniqueId)
  }

}
