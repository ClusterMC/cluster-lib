package org.clustermc.lib.player

import java.util.UUID

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.plugin.java.JavaPlugin
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.data.KeyLoadingCoordinator
import org.clustermc.lib.utils.ClusterServerPlugin

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Default (Template) Project.
 * 
 * Default (Template) Project can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

//apply(key) = this(key) = get(key) = PlayerCoordinator(key)
abstract class PlayerCoordinator[T <: PlayerWrapper]() extends KeyLoadingCoordinator[UUID, T]{
  val dbName = JavaPlugin.getProvidingPlugin(this.getClass).asInstanceOf[ClusterServerPlugin].server
  val index = "uuid"

  override def unloadAll(): Unit = coordinatorMap.keysIterator.foreach(unload)

  /** ONLY USE AFTER CHECKING IF THE PLAYER IS LOADED CURRENTLY */
  def loadData(uuid: UUID, key: String): Option[String] = {
    val responce: String = collection().find(new Document(index, uuid.toString)).first().getString(key)
    if(responce == null) None else Option(responce)
  }

  override def unload(key: UUID): Unit = {
    if (loaded(key)) {
      beforeUnload(key)
      apply(key).save(collection())
      remove(key)
    }
  }

  override def load(uuid: UUID): Unit = {
    if (!loaded(uuid)) {
      val player: T = new T(uuid)
      if (collection().count(new Document(index, uuid.toString), new CountOptions().limit(1)) == 1){
        player.load(collection().find(new Document(index, uuid.toString)).first())
        afterLoad(player)
      } else {
        afterLoad(player)
        player.save(collection())
      }
      set(uuid, player)
    }
  }

  def collection(): MongoCollection[Document] = {
    ClusterLib.instance.database.getDatabase(dbName).getCollection("playerdata")
  }

  protected def beforeUnload(uuid: UUID)

  protected def afterLoad(player: T)

}