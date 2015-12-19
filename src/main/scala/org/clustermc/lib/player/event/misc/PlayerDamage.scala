package org.clustermc.lib.player.event.misc

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.{EventHandler, EventPriority, Listener}
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.player.libplayer.LibPlayer

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object PlayerDamage extends Listener{

  Bukkit.getServer.getPluginManager.registerEvents(this, ClusterLib.instance)

  @EventHandler(priority = EventPriority.LOWEST)
  def onPlayerDamage(event: EntityDamageEvent): Unit ={
    if(event.getEntity.isInstanceOf[Player]){
      if(!LibPlayer(event.getEntity.getUniqueId).playerDamageable){
        event.setCancelled(true)
      }
    }
  }

}
