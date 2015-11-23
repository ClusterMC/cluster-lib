package org.clustermc.lib.player.storage

import java.util.UUID

import org.bson.Document
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.data.KeyLoadingCoordinator
import org.clustermc.lib.data.values.mutable.BooleanSetting
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

  //TODO turn this into a functioning playerobject that can have its handler overriden

}

//apply(key) = this(key) = get(key) = PlayerCoordinator(key)
object PlayerCoordinator extends KeyLoadingCoordinator[UUID, ClusterPlayer] {
  val index = "uuid"
  val collection = "playerdata"

  override def unload(key: UUID): Unit = {
    if (has(key)) {
      apply(key).channelStorage.subscribedChannels.foreach(c => c.leave(key))
      apply(key).save(ClusterLib.instance.database)
      remove(key)
    }
  }

  override def unloadAll(): Unit = coordinatorMap.keysIterator.foreach(unload)

  override def load(uuid: UUID): Unit = {
    if (!has(uuid)) {
      val player: ClusterPlayer = new T(uuid)
      player.load(
        ClusterLib.instance.database.getDatabase("data").getCollection(collection)
          .find(new Document(index, uuid.toString))
          .first())
      player.channelStorage.focus(Channel.get("general").get)
    }
  }
}