package org.clustermc.lib.gui.menu

import io.mazenmc.menuapi.menu.Menu
import net.minecraft.server.v1_8_R3.ItemStack
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.utils.cooldown.CooldownExecutor

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
@Deprecated
object MenuUtil {

  def displayColor(player: Player, seconds: Int, menu: Menu, item: ItemStack): Unit ={
    player.closeInventory()
    Bukkit.getScheduler.scheduleSyncDelayedTask(ClusterLib.instance, new Runnable() {
      override def run(): Unit = {

        ClusterLib.instance.cooldowns.add(player.getUniqueId, "menu", seconds, new CooldownExecutor {
          override def use(player: Player, ability: String): Unit = if (player != null) menu.showTo(player)
        })
      }
    }, 1)
  }

}
