package org.clustermc.lib.player.statsplayer

import java.util.UUID

import com.mongodb.client.MongoCollection
import org.bson.Document
import org.clustermc.lib.player.{PlayerCoordinator, PlayerWrapper}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class StatsPlayer(uuid: UUID) extends PlayerWrapper(uuid){
  override def save(database: MongoCollection[Document]): Unit = ???

  override def toDocument: Document = ???

  override def load(doc: Document): Unit = ???
}

object StatsPlayer extends PlayerCoordinator[StatsPlayer] {
  /**
    * Get a new instance of the wrapper that this class stores
    *
    * @param uuid The uuid of the player who will be instanced into the return
    * @return an instance of the class that this coordinator coordinates
    */
  override protected def genericInstance(uuid: UUID): StatsPlayer = ???

  /**
    * Executed before the player is unloaded from the server cache
    *
    * @param uuid The uuid of the player who is being unloaded
    */
  override protected def beforeUnload(uuid: UUID): Unit = ???

  /**
    * Executed after the player is laded from the database and before they're entered
    * Into the coordinator cache
    *
    * @param uuid The uuid of the player who is being loaded
    */
  override protected def afterLoad(uuid: UUID): Unit = ???
}