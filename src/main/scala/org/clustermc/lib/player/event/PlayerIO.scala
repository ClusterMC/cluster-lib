package org.clustermc.lib.player.event

import org.bukkit.Bukkit
import org.bukkit.event.player.{AsyncPlayerPreLoginEvent, PlayerKickEvent, PlayerLoginEvent, PlayerQuitEvent}
import org.bukkit.event.{EventHandler, EventPriority, Listener}
import org.bukkit.plugin.java.JavaPlugin
import org.clustermc.lib.player.storage.PlayerCoordinator
import org.clustermc.lib.punishment.data.Punishment

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class PlayerIO extends Listener {

  Bukkit.getServer.getPluginManager.registerEvents(this, JavaPlugin.getProvidingPlugin(this.getClass))

  @EventHandler(priority = EventPriority.LOWEST)
  def prelogin(event: AsyncPlayerPreLoginEvent): Unit ={
    val player = PlayerCoordinator(event.getUniqueId)
    if(player.banned){
      event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Punishment.reason(player.punishments._ban.get))
      PlayerCoordinator.unload(event.getUniqueId)
    }
  }

  @EventHandler def login(event: PlayerLoginEvent): Unit = {
    if(!PlayerCoordinator.has(event.getPlayer.getUniqueId)) PlayerCoordinator(event.getPlayer.getUniqueId)
  }

  @EventHandler def kicked(event: PlayerKickEvent): Unit =
    PlayerCoordinator.unload(event.getPlayer.getUniqueId)

  @EventHandler def disconnected(event: PlayerQuitEvent): Unit =
    PlayerCoordinator.unload(event.getPlayer.getUniqueId)
}
