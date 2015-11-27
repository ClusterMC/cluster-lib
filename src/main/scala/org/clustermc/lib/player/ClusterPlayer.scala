package org.clustermc.lib.player

import java.time.Instant
import java.util.UUID

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import org.bson.types.ObjectId
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.econ.Bank
import org.clustermc.lib.enums.{DonatorRank, PermissionRank}
import org.clustermc.lib.player.storage.{AchievementStorage, ChannelStorage, PunishmentStorage}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class ClusterPlayer(uuid: UUID) extends PlayerWrapper(uuid){
  //----------MISC----------
  var showPlayers: Boolean = true
  lazy val latestName: String = offlineBukkitPlayer.getName

  //----------PERMISSIONS----------
  var group: PermissionRank = PermissionRank.MEMBER
  var donator: DonatorRank = DonatorRank.NONE
  def hasRank(permGroup: PermissionRank): Boolean = group.ordinal() >= permGroup.ordinal()
  def hasDonatorRank(permGroup: DonatorRank): Boolean = donator.ordinal() >= permGroup.ordinal()
  def chatRank = {
    if(hasDonatorRank(DonatorRank.SAGA) && !hasRank(PermissionRank.MOD)) donator.strings
    else group.strings
  }

  //----------ACHIEVEMENTS----------
  val achievements = new AchievementStorage()

  //----------ECONOMY----------
  var bank: Bank = new Bank()

  //----------CHAT----------
  val channelStorage = new ChannelStorage(this.bukkitPlayer)
  var chatMention, receiveMessages: Boolean = true

  //----------PUNISHMENTS----------
  val punishments: PunishmentStorage = new PunishmentStorage
  def banned = punishments.banned ; def muted = punishments.muted

  override def save(database: MongoCollection[Document]): Unit = {
    database.updateOne(new Document(ClusterPlayer.index, uuid), toDocument,
      new UpdateOptions().upsert(true))
  }
  override def toDocument: Document = {
    new Document()
      .append("uuid", uuid.toString)
      .append("name", latestName)
      .append("groups", new Document()
        .append("group", group.toString)
        .append("donator", donator.toString))
      .append("bank", bank.serialize())
      .append("settings", new Document()
        .append("chatMention", chatMention)
        .append("showPlayers", showPlayers)
        .append("receiveMessages", receiveMessages))
      .append("punishments", new Document()
        .append("ban", if(banned) punishments._ban.get.toString else "none")
        .append("mute", if(muted) punishments._mute.get.toString else "none"))
      .append("achievements", achievements.serialize())
      .append("lastOnline", Instant.now())
  }
  override def load(doc: Document): Unit = {
    group = PermissionRank.valueOf(doc.getString("groups.group"))
    donator = DonatorRank.valueOf(doc.getString("groups.donator"))

    bank = new Bank(doc.getString("bank"))

    chatMention = doc.getBoolean("settings.chatMention")
    showPlayers = doc.getBoolean("settings.showPlayers")
    receiveMessages = doc.getBoolean("settings.receiveMessages")

    achievements.deserialize(doc.getString("achievements"))

    //load punishments
    var hexString = doc.getString("punishments.ban")
    if (hexString != "none") {
      punishments._ban = Option(new ObjectId(hexString))
      punishments.banned
    }
    hexString = doc.getString("punishments.mute")
    if (hexString != "none") {
      punishments._mute = Option(new ObjectId(hexString))
      punishments.muted
    }

  }
}

object ClusterPlayer extends PlayerCoordinator[ClusterPlayer]{
  protected override def beforeUnload(uuid: UUID): Unit = {
    apply(uuid).channelStorage.subscribedChannels.foreach(c => c.leave(uuid))
  }

  protected override def afterLoad(player: ClusterPlayer): Unit = {
    player.channelStorage.focus(Channel.get("general").get)
  }
}
