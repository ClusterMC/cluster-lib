package org.clustermc.lib.xserver

import java.io.{ByteArrayInputStream, DataInputStream}

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import org.clustermc.lib.xserver.receivers.{AlertReceiver, CommandReceiver, PunishmentReceiver, SaveReceiver}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object XMessageReceive extends PluginMessageListener {

    //TODO
    override def onPluginMessageReceived(channel: String, player: Player, message: Array[Byte]): Unit = {
        if(!channel.equals("BungeeCord")) {
            return
        }

        val in = ByteStreams.newDataInput(message)
        val subChannel = in.readUTF()

        val msgbytes = new Array[Byte](in.readShort().toInt)
        in.readFully(msgbytes)
        val data = new DataInputStream(new ByteArrayInputStream(msgbytes)).readUTF()

        subChannel.toUpperCase match {
            case CommandId.id => CommandReceiver.receive(data)
            case AlertId.id => AlertReceiver.receive(data)
            case SaveId.id => SaveReceiver.receive(data)
            case msg if msg.contains(PunishmentId.id) => PunishmentReceiver.receive(data)
            case _ => //don't know if we'll use this but w/e
        }

    }
}

sealed trait MessageId { def id: String }
case object CommandId extends MessageId { val id = "COMMAND" }
case object PunishmentId extends MessageId { val id = "PUNISHMENT" }
case object AlertId extends MessageId { val id = "ALERT" }
case object SaveId extends MessageId { val id = "SAVE" }


