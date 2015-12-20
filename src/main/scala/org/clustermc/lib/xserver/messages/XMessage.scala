package org.clustermc.lib.xserver.messages

import java.io.{ByteArrayOutputStream, DataOutputStream}

import com.google.common.io.ByteStreams
import org.clustermc.lib.xserver.XMessageIdentifier

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

trait XMessage{

  /**
    * https://www.spigotmc.org/wiki/bukkit-bungee-plugin-messaging-channel/
    */
  val action: String

  /**
    * Sends a plugin message across servers
    *
    * @param specifier Line two is the data, depending on the request
    *                  ex: "ALL" or "ONLINE" servers.. or (playername)
    * @param identifier Identifier identifies what to do with data when retrieved
    *                   ex: "BAN" "KICK" or "CHAT_DONATOR"
    * @param data Data is the main instruction sent over the server
    */
  def send(specifier: String, identifier: XMessageIdentifier, data: String): Unit ={
    //The output that will be sent cross-server
    val output = ByteStreams.newDataOutput()
    //Write the channel to distinguish what will be happening to this data
    output.writeUTF(action)
    //A parameter to the channel data, further distinguishing what will happen
    output.writeUTF(specifier)
    /* The sub channel, readable by the receiver to
     * Quoting Spigot: "Make sure this data is yours"
     * But since the only plugins on this server /are/ ours,
     * We can use this field to specify a syntax for what
     * To do with the data */
    output.writeUTF(identifier.name)

    //To store the data as bytes so we can write it out into a byte array
    val mByteOut = new ByteArrayOutputStream()
    //Store the data
    val mDataOut = new DataOutputStream(mByteOut)
    //Write the data to the data store
    mDataOut.writeUTF(data)

    //Write array size of the byte converted data
    output.writeShort(mByteOut.toByteArray.length)
    //Write byte converted data
    output.write(mByteOut.toByteArray)
  }

  def send(): Unit

}

object XMessage extends Enumeration {
  /*
  type ActionResult = Value
  /**
    * Player doesn't exist in the database
    */
  val NO_EXIST = Value("NO_EXIST")
  */
  //TODO totally forgot what I was going to use this for.
}

