package org.clustermc.lib.chat.announcer

import org.clustermc.lib.ClusterLib
import org.clustermc.lib.utils.CustomConfig

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object Announcer {
  var announcements: Array[Announcement] = {
    val config = new CustomConfig(ClusterLib.instance.getDataFolder, "announcements").getConfigurationSection("messages")
    config.getKeys(false).forEach(string => new Announcement(config.getStringList(string)) :: announcements)
  }

  def start(): Unit ={
    //start the cooldown shit
  }

}
