package org.clustermc.lib.chat.announcer

import org.clustermc.lib.ClusterLib
import org.clustermc.lib.utils.CustomConfig
import scala.collection.JavaConverters._

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object Announcer {
    var announcements: List[Announcement] = {
        val config = new CustomConfig(ClusterLib.instance.getDataFolder, "announcements").getConfigurationSection("messages")
        config.getKeys(false).asScala.map(s => Announcement(config.getStringList(s))).toList
    }

    def start(): Unit = {
        //start the cooldown shit
    }

}
