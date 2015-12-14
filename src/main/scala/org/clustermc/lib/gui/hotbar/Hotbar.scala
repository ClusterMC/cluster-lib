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

/**
  * Represents a hotbar shown to a player (the 9 slots that a player sees
  * while they are outside their inventory)
  *
  * @param items an array of HotbarItem(s) with a length of 9
  */
@throws[ArrayIndexOutOfBoundsException]
abstract class Hotbar(val items: Array[HotbarItem]) {

  if (items.length != 9) throw new ArrayIndexOutOfBoundsException

  /**
    * Represents the name of this hotbar, for use in the coordinator
    */
  lazy val name: String = this.getClass.getSimpleName

  /**
    * Player uses the item in Slot by doing an Action
    *
    * @param player The player who used the item
    * @param slot The slot in the hotbar that was acted upon
    * @param action The type of click the player did on the item
    */
  def use(player: Player, slot: Int, action: Action): Unit = {
    if (slot <= 8 && slot >= 0)
      items(slot).click(player, action)
  }

  /**
    * Update the player's inventory to have these items in it
    *
    * @param player The player whose inventory will be updated
    */
  def send(player: Player): Unit = {
    player.getInventory.clear()
    player.updateInventory()
    for (i <- 0 to 8) player.getInventory.setItem(i, items(i).stack)
    player.updateInventory()
  }

}

object Hotbar {
  private var hotbars: Map[String, Hotbar] = TreeMap()(CaseInsensitiveOrdered)

  /**
    * Register a hotbar to the hotbar map
    *
    * @param hotbar The hotbar to be added
    */
  def register(hotbar: Hotbar): Unit = {
    hotbars = hotbars + (hotbar.name.toLowerCase -> hotbar)
  }

  /**
    * Return the hotbar retrieved by this name
    *
    * @param name the hotbar to retrieve
    * @return The hotbar that was retrieved
    */
  def get(name: String): Hotbar = hotbars.get(name.toLowerCase).get
}
