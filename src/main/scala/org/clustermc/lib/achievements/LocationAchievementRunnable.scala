package org.clustermc.lib.achievements

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.utils.TitleAPI
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

import scala.collection.JavaConverters._

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

//TODO we need to make location achievements per-server
object LocationAchievementRunnable {

  var task: BukkitTask = null

  def end(): Unit ={
    task.cancel()
  }

  def doChecks(): Unit ={
    for(x <- LocationAchievements.values()){
      val pl = x.loc.getWorld.getNearbyEntities(x.loc,  x.radius, x.radius, x.radius).asScala.filter(e => e.isInstanceOf[Player])
      pl.foreach{ p =>
        if(ClusterPlayer(p.getUniqueId).achievements.unlock(x)){
          TitleAPI.sendTitle(Bukkit.getPlayer(p.getUniqueId), int2Integer(5), int2Integer(60), int2Integer(5),
            Messages("achievement.title.title", MsgVar("{NAME}", x.name)),
            Messages("achievement.title.subtitle", MsgVar("{DESCRIPTION}", x.witthsubtitle)))
        }
      }
    }
  }

  def start(): Unit = {
    task = Bukkit.getServer.getScheduler.runTaskTimerAsynchronously(ClusterLib.instance, new Runnable {
      override def run(): Unit = doChecks()
    }, 200L, 20 * 3L)
  }

}
