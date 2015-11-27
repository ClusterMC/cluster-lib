package org.clustermc.lib.gui.hotbar.events

import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.{EventHandler, Listener}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class DropItemEvent extends Listener {

    //TODO fix for servers that need item drops

    @EventHandler
    def onDrop(event: PlayerDropItemEvent): Unit = {
        if(!event.getPlayer.isOp) {
            event.setCancelled(true)
        }
    }

}
