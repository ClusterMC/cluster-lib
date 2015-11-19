package org.clustermc.lib.player.event

import org.bukkit.Bukkit
import org.bukkit.event.player.{PlayerKickEvent, PlayerLoginEvent, PlayerQuitEvent}
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.plugin.java.JavaPlugin
import org.clustermc.lib.player.PlayerCoordinator

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class PlayerIO(coordinator: PlayerCoordinator) extends Listener {

  Bukkit.getServer.getPluginManager.registerEvents(this, JavaPlugin.getProvidingPlugin(this.getClass))

  @EventHandler def login(event: PlayerLoginEvent): Unit =
    coordinator(event.getPlayer.getUniqueId)

  @EventHandler def kicked(event: PlayerKickEvent): Unit =
    coordinator.unload(event.getPlayer.getUniqueId)

  @EventHandler def disconnected(event: PlayerQuitEvent): Unit =
    coordinator.unload(event.getPlayer.getUniqueId)
}
