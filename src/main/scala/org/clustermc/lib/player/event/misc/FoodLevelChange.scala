package org.clustermc.lib.player.event.misc

import org.bukkit.Bukkit
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.{EventHandler, Listener}
import org.clustermc.lib.ClusterLib

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object FoodLevelChange extends Listener{

  Bukkit.getServer.getPluginManager.registerEvents(this, ClusterLib.instance)

  @EventHandler
  def itemDamage(event: FoodLevelChangeEvent){
    event.setFoodLevel(20)
    event.setCancelled(true)
  }
}
