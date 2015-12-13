package org.clustermc.lib.player.event

import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.event.player.{AsyncPlayerPreLoginEvent, PlayerKickEvent, PlayerLoginEvent, PlayerQuitEvent}
import org.bukkit.event.{EventHandler, EventPriority, Listener}
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.player.ClusterPlayer
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
    val player = ClusterPlayer(event.getUniqueId)
    if(player.banned){
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Punishment.reason(player.punishments._ban.get))
      ClusterPlayer.unload(event.getUniqueId)
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  def login(event: PlayerLoginEvent): Unit = {
    if(!ClusterPlayer.loaded(event.getPlayer.getUniqueId) && !event.getResult.toString.contains("KICK")){
      val p = ClusterPlayer(event.getPlayer.getUniqueId)
      p.latestName = event.getPlayer.getName
      ClusterLib.instance.database.getDatabase.getCollection("online").insertOne(
        new Document().append("uuid", p.itemId.toString).append("server", ClusterLib.instance.serverName))
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  def kicked(event: PlayerKickEvent): Unit = {
    ClusterPlayer(event.getPlayer.getUniqueId).latestName = event.getPlayer.getName
    ClusterPlayer.unload(event.getPlayer.getUniqueId)
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  def disconnected(event: PlayerQuitEvent): Unit = {
    ClusterPlayer(event.getPlayer.getUniqueId).latestName = event.getPlayer.getName
    ClusterPlayer.unload(event.getPlayer.getUniqueId)
  }

}
