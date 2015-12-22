package org.clustermc.lib.utils

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class ClusterServerPlugin(val server: String) extends JavaPlugin{

  def serverName: String = this.getServer.getServerName

    def registerEventListeners(listeners: Listener*) = listeners.foreach(this.getServer.getPluginManager.registerEvents(_, this))

}
