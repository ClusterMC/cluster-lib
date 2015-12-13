package org.clustermc.lib.chat.listener

import org.bukkit.Bukkit
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.{EventHandler, EventPriority, Listener}
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.chat.event.CPlayerChatEvent

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
class ChatListener extends Listener {

  Bukkit.getServer.getPluginManager.registerEvents(this, ClusterLib.instance)

    @EventHandler(priority = EventPriority.LOWEST)
    def asyncChat(event: AsyncPlayerChatEvent): Unit = {
        CPlayerChatEvent.processChat(event.getPlayer, event.getMessage)
        event.setCancelled(true)
    }
}
