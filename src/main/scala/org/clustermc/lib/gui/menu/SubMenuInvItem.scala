package org.clustermc.lib.gui.menu

import java.util.UUID

import io.mazenmc.menuapi.menu.Menu
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.clustermc.lib.ClusterLib

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Default (Template) Project.
 * 
 * Default (Template) Project can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

trait SubMenuInvItem extends InvItem {
    def menu(player: Player): Menu

    def canOpen(uuid: UUID): Boolean

    override def act(player: Player, clickType: ClickType): Unit = {
        if(canOpen(player.getUniqueId)) {
            player.closeInventory()
            Bukkit.getScheduler.scheduleSyncDelayedTask(ClusterLib.instance, new Runnable() {
                override def run(): Unit = if(player != null) {menu(player).showTo(player) }
            }, 1)
        }
    }
}
