package org.clustermc.lib.gui.menu.purchase

import java.util.UUID

import io.mazenmc.menuapi.menu.Menu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.clustermc.lib.gui.menu.SubMenuInvItem

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class PurchaseItem extends SubMenuInvItem{
  val cost: Int

  override def menu(player: Player): Menu = ???

  override def canOpen(uuid: UUID): Boolean = ???

  override val item: ItemStack = _
}

abstract class RentPurchaseItem extends PurchaseItem{
  val rent: Int

}
