package org.clustermc.lib.chat.announcer

import org.bukkit.Bukkit
import org.clustermc.lib.utils.StringUtil

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class Announcement(messages: List[String]) {
  def send(): Unit ={
    messages.foreach(msg => Bukkit.getServer.broadcastMessage(StringUtil.colorString(msg)))
  }
}
