package org.clustermc.lib.player

import java.util.UUID

import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bukkit.Bukkit
import org.clustermc.lib.data.DataItem
import org.clustermc.lib.utils.database.MongoLoadable

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class PlayerWrapper[C <: PlayerWrapper[C]](playerId: UUID) extends DataItem(playerId) with MongoLoadable{

  def bukkitPlayer = Bukkit.getPlayer(playerId)
  
  def offlineBukkitPlayer = Bukkit.getOfflinePlayer(playerId)

  def message(string: String): Unit = bukkitPlayer.sendMessage(string)

  def save(database: MongoCollection[Document])
}
