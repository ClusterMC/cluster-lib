package org.clustermc.lib.chat.announcer

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitTask
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.utils.CustomConfig

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object Announcer {
    var task: BukkitTask = null

    val announcements: ListBuffer[Announcement] = {
        val config = new CustomConfig(ClusterLib.instance.getDataFolder, "announcements")
          .getConfigurationSection("messages")
        config.getKeys(false).asScala.toList.map(s => Announcement(config.getStringList(s))).to[ListBuffer[Announcement]]
    }

    def end(): Unit ={
        task.cancel()
    }

    def start(): Unit = {
        task = Bukkit.getServer.getScheduler.runTaskTimerAsynchronously(ClusterLib.instance, new Runnable {
            override def run(): Unit = {
                val ann = announcements.remove(0).send()
                announcements.insert(announcements.length, ann)
            }
        }, 200L, 20*120L)
    }

}
