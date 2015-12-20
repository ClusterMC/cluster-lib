package org.clustermc.lib.xserver.receivers

import org.bukkit.Bukkit
import org.clustermc.lib.xserver.XMessageReceiver

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object CommandReceiver extends XMessageReceiver{
  override def receive(identifier: String, message: String): Unit = {
    Bukkit.dispatchCommand(Bukkit.getConsoleSender, message)
  }
}
