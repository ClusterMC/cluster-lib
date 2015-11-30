package org.clustermc.lib.gui.hotbar.events

import org.bukkit.Bukkit
import org.bukkit.event.inventory.{InventoryClickEvent, InventoryDragEvent}
import org.bukkit.event.{EventHandler, EventPriority, Listener}
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

object MoveItemEvent extends Listener {

    Bukkit.getServer.getPluginManager.registerEvents(this, ClusterLib.instance)

    @EventHandler(priority = EventPriority.HIGHEST)
    def onTryMoveItem(event: InventoryClickEvent): Unit = {
        if(!event.getWhoClicked.isOp) {
            if(!ClusterPlayer(event.getWhoClicked.getUniqueId).itemsMovable){
                event.setCancelled(true)
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    def onTryDrag(event: InventoryDragEvent): Unit = {
        if(!event.getWhoClicked.isOp) {
            if(!ClusterPlayer(event.getWhoClicked.getUniqueId).itemsMovable){
                event.setCancelled(true)
            }
        }
    }

}
