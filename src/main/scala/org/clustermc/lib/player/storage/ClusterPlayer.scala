package org.clustermc.lib.player.storage

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.data.KeyLoadingCoordinator
import org.clustermc.lib.data.values.mutable.impl.SettingDataValues.BooleanSetting
import org.clustermc.lib.econ.Bank
import org.clustermc.lib.player.PlayerWrapper
import org.clustermc.lib.utils.database.MongoObject
import org.clustermc.lib.{ClusterLib, PermGroup}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class ClusterPlayer(uuid: UUID) extends PlayerWrapper(uuid) with MongoObject {
  //----------MISC----------
  val showPlayers = BooleanSetting(true, true)
  lazy val latestName: String = offlineBukkitPlayer.getName
  var _new = true

  //----------PERMISSIONS----------
  var group: PermGroup = PermGroup.MEMBER
  def hasRank(permGroup: PermGroup): Boolean = group.ordinal() >= permGroup.ordinal()

  //----------ECONOMY----------
  val bank: Bank = new Bank()

  //----------CHAT----------
  val channelStorage = new ChannelStorage(this.bukkitPlayer)
  val chatMention, receiveMessages = BooleanSetting(true, true)

  //----------PUNISHMENTS----------
  val punishments: PunishmentStorage = new PunishmentStorage
  def banned = punishments.banned ; def muted = punishments.muted
}

//apply(key) = this(key) = get(key) = PlayerCoordinator(key)
class PlayerCoordinatorz[T <: ClusterPlayer] extends KeyLoadingCoordinator[UUID, T] {
  val index = "uuid"
  val collection = "playerdata"

  def playerType(): T = Class[T]

  override def unload(key: UUID): Unit = {
    if (loaded(key)) {
      apply(key).channelStorage.subscribedChannels.foreach(c => c.leave(key))
      apply(key).save(ClusterLib.instance.database)
      remove(key)
    }
  }

  override def unloadAll(): Unit = coordinatorMap.keysIterator.foreach(unload)

  override def load(uuid: UUID): Boolean = {
    if (!loaded(uuid)) {
      val player: T = new T(uuid) //TODO
      val db = ClusterLib.instance.database.getDatabase("data").getCollection(collection)
      if(db.count(new Document(index, uuid.toString), new CountOptions().limit(1)) == 1){
        player.load(db.find(new Document(index, uuid.toString)).first())
        player.channelStorage.focus(Channel.get("general").get)
        player._new = false
      }
      set(uuid, player)
    }
    true
  }

  /**
   * ONLY USE AFTER CHECKING IF THE PLAYER IS LOADED CURRENTLY
   */
  def load(uuid: UUID, key: String): Option[String] = {
    val responce: String = ClusterLib.instance.database.
      getDatabase("data").getCollection(collection)
      .find(new Document(index, uuid.toString)).first()
      .getString(key)
    if(responce == null) None else Option(responce)
  }

}
object PlayerCoordinator{
  private var coordinator: PlayerCoordinatorz[_ <: ClusterPlayer] = null

  def init[T <: ClusterPlayer](): Unit ={
    coordinator = new PlayerCoordinatorz[T]
  }

  def apply(key: UUID) = coordinator(key)
  def loaded(key: UUID): Boolean = coordinator.loaded(key)
  def remove(key: UUID) = coordinator.remove(key)
  def load(uuid: UUID, key: String): Option[String] = coordinator.load(uuid, key)
  def load(uuid: UUID): Boolean = coordinator.load(uuid)
  def unloadAll() = coordinator.unloadAll()
  def unload(key: UUID) = coordinator.unload(key)
}
