package org.clustermc.lib.gui.hotbar.events

import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.{EventHandler, Listener}
import org.clustermc.lib.gui.hotbar.Hotbar

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class InteractEvent extends Listener {

    //TODO fix for servers that need to be able to click

    @EventHandler
    def onClick(event: PlayerInteractEvent) {
        if(!event.hasItem || !event.getAction.name.contains("CLICK")) {
            return
        }
        event.setCancelled(true)
        val name: String = event.getPlayer.getMetadata("inventory").get(0).asString
        if(name != null && !(name == "")) {
            Hotbar.get(name).use(event.getPlayer, event.getPlayer.getInventory.getHeldItemSlot, event.getAction)
        }
    }

}
