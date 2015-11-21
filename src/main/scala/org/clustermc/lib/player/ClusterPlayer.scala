package org.clustermc.lib.player

import java.util.UUID

import org.bson.Document
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.data.KeyLoadingCoordinator
import org.clustermc.lib.data.values.mutable.BooleanSetting
import org.clustermc.lib.econ.Bank
import org.clustermc.lib.player.storage.ChannelStorage
import org.clustermc.lib.punishment.data.{BanData, MuteData}
import org.clustermc.lib.utils.database.MongoObject

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class ClusterPlayer(uuid: UUID) extends PlayerWrapper(uuid) with MongoObject{

  val chatMention, showPlayers, receiveMessages = BooleanSetting(true, true)
  val channelStorage = new ChannelStorage(this.bukkitPlayer)
  val bank: Bank = new Bank()

  //TODO static data

  def muted(): Option[MuteData] = {

  }

  def banned(): Option[BanData] = {

  }

  //TODO turn this into a functioning playerobject that can have its handler overriden

}

//apply(key) = this(key) = get(key) = PlayerCoordinator(key)
trait PlayerCoordinator[T <: ClusterPlayer] extends KeyLoadingCoordinator[UUID, T] {
  val index = "uuid"
  val collection = "playerdata"

  override def unload(key: UUID): Unit = {
    if(has(key)) {
      apply(key).channelStorage.subscribedChannels.foreach(c => c.leave(key))
      apply(key).save(ClusterLib.instance.database)
      remove(key)
    }
  }

  override def unloadAll(): Unit = coordinatorMap.keysIterator.foreach(unload)

  override def load(uuid: UUID): Unit = {
    if(!has(uuid)) {
      val player: ClusterPlayer = new T(uuid)
      player.load(
        ClusterLib.instance.database.getCollections.getCollection(collection)
          .find(new Document(index, uuid.toString))
          .first())
      player.channelStorage.setFocusedChannel(None)
    }
  }
}
