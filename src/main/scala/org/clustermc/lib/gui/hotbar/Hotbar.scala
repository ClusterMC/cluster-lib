package org.clustermc.lib.gui.hotbar

import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.clustermc.lib.utils.CaseInsensitiveOrdered

import scala.collection.immutable.TreeMap


/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
@throws[ArrayIndexOutOfBoundsException]
abstract class Hotbar(val items: Array[HotbarItem]) {

    lazy val name: String = this.getClass.getSimpleName

    if(items.length != 9) {
        throw new ArrayIndexOutOfBoundsException
    }

    def use(player: Player, slot: Int, action: Action): Unit = {
        if(slot <= 8 && slot >= 0)
            items(slot).click(player, action)
    }

    def send(player: Player): Unit = {
        player.getInventory.clear()
        player.updateInventory()
        for(i <- 0 to 8) player.getInventory.setItem(i, items(i).stack)
        player.updateInventory()
    }

}

object Hotbar {
    private var hotbars: Map[String, Hotbar] = TreeMap()(CaseInsensitiveOrdered)

    def register(name: String, hotbar: Hotbar): Unit ={
        hotbars = hotbars + (name -> hotbar)
    }

    def get(name: String): Hotbar = hotbars.get(name).get
}
