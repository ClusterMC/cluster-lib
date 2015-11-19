package org.clustermc.lib.gui.hotbar.events

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.{EventHandler, Listener}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class MoveItemEvent extends Listener {

    @EventHandler
    def onTryMoveItem(event: InventoryClickEvent): Unit = {
        if(!event.getWhoClicked.isOp) {
            event.setCancelled(true)
        }
    }

}
