package org.clustermc.lib.gui.hotbar.events

import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.{EventHandler, Listener}
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.gui.hotbar.Hotbar
import org.clustermc.lib.player.ClusterPlayer

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object InteractEvent extends Listener {

  Bukkit.getServer.getPluginManager.registerEvents(this, ClusterLib.instance)

  @EventHandler
  def onClick(event: PlayerInteractEvent) {
    if (event.hasBlock && !ClusterPlayer(event.getPlayer.getUniqueId).blocksBreakable) {
      event.setCancelled(true)
    }
    if (event.hasItem && !ClusterPlayer(event.getPlayer.getUniqueId).canInteract) {
      event.setCancelled(true)
      val name: String = event.getPlayer.getMetadata("inventory").get(0).asString
      if (name != null && !(name == "")) {
        Hotbar.get(name).use(event.getPlayer, event.getPlayer.getInventory.getHeldItemSlot, event.getAction)
      }
    }
  }

}
