package org.clustermc.lib.gui.hotbar.events

import org.bukkit.Bukkit
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.{EventHandler, Listener}
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.player.ClusterPlayer

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object DropItemEvent extends Listener {

    Bukkit.getServer.getPluginManager.registerEvents(this, ClusterLib.instance)

    @EventHandler
    def onDrop(event: PlayerDropItemEvent): Unit = {
        if(!ClusterPlayer(event.getPlayer.getUniqueId).itemsDroppable) {
            event.setCancelled(true)
        }
    }

}
