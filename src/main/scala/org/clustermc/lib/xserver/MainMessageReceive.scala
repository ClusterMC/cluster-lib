package org.clustermc.lib.xserver

import java.io.{ByteArrayInputStream, DataInputStream}

import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object MainMessageReceive extends PluginMessageListener{

  //TODO
  override def onPluginMessageReceived(channel: String, player: Player, message: Array[Byte]): Unit = {
    if (!channel.equals("BungeeCord")) return

    val in = ByteStreams.newDataInput(message)
    val subChannel = in.readUTF()

    val msgbytes = new Array[Byte](in.readShort().toInt)
    in.readFully(msgbytes)
    val data = new DataInputStream(new ByteArrayInputStream(msgbytes)).readUTF()

    subChannel.toLowerCase match {
      case "command" => Bukkit.dispatchCommand(Bukkit.getConsoleSender, data)
    }

  }
}
