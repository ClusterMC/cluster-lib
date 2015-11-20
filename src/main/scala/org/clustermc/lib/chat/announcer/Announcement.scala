package org.clustermc.lib.chat.announcer

import java.util

import org.bukkit.Bukkit
import org.clustermc.lib.utils.StringUtil

import scala.collection.JavaConverters._

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

case class Announcement(messages: List[String]) {
    def send() = messages.foreach(msg => Bukkit.getServer.broadcastMessage(StringUtil.colorString(msg)))
}

object Announcement {
    def apply(msgs: util.List[String]) = Announcement(msgs.asScala.toList)
}
