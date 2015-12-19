package org.clustermc.lib.player.libplayer

import java.time.Instant
import java.util.UUID

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.{Filters, UpdateOptions}
import org.bson.Document
import org.clustermc.lib.econ.Bank
import org.clustermc.lib.enums.{DonatorRank, PermissionRank}
import org.clustermc.lib.player.libplayer.storage.{AchievementStorage, ChannelStorage, PunishmentStorage}
import org.clustermc.lib.player.{PlayerCoordinator, PlayerWrapper}
import org.json.JSONObject

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class LibPlayer(uuid: UUID) extends PlayerWrapper(uuid){
  //----------MISC----------
  var showPlayers: Boolean = true
  var latestName: String = "ERROR"
  var itemsDroppable, canInteract, blocksBreakable, itemsDamageable,
  itemsMovable, playerKillable, playerDamageable: Boolean = false
  var loginServer: String = "Hub"

  //----------PERMISSIONS----------
  var group: PermissionRank = PermissionRank.MEMBER
  var donator: DonatorRank = DonatorRank.NONE
  def hasRank(permGroup: PermissionRank): Boolean = group.ordinal() >= permGroup.ordinal()
  def hasDonatorRank(permGroup: DonatorRank): Boolean = donator.ordinal() >= permGroup.ordinal()
  def hasRank(string: String): Boolean = {
    if(PermissionRank.contains(string)){
      return hasRank(PermissionRank.valueOf(string.toUpperCase))
    } else if(DonatorRank.contains(string)){
      return hasDonatorRank(DonatorRank.valueOf(string.toUpperCase))
    }
    false
  }
  def chatRank = {
    if(hasDonatorRank(DonatorRank.SAGA) && !hasRank(PermissionRank.MOD)) donator.strings
    else group.strings
  }

  //----------ACHIEVEMENTS----------
  val achievements = new AchievementStorage()

  //----------ECONOMY----------
  var bank: Bank = new Bank()

  //----------CHAT----------
  lazy val channelStorage = new ChannelStorage(this.uuid)
  var chatMention, receiveMessages: Boolean = true

  //----------PUNISHMENTS----------
  val punishments: PunishmentStorage = new PunishmentStorage
  def banned = punishments.banned ; def muted = punishments.muted

  override def save(database: MongoCollection[Document]): Unit = {
    database.updateOne(
      Filters.eq(LibPlayer.index, uuid.toString),
      new Document("$set", toDocument),
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
        .append("loginServer", loginServer)
        .append("chatMention", chatMention)
        .append("showPlayers", showPlayers)
        .append("receiveMessages", receiveMessages))
      .append("punishments", new Document()
        .append("ban", if(banned) punishments._ban.get.toString else "none")
        .append("mute", if(muted) punishments._mute.get.toString else "none"))
      .append("achievements", achievements.serialize())
      .append("lastOnline", Instant.now().toString)
  }

  override def load(doc: Document): Unit = {
    val obj = new JSONObject(doc.toJson)
    group = PermissionRank.valueOf(obj.getJSONObject("groups").getString("group"))
    donator = DonatorRank.valueOf(obj.getJSONObject("groups").getString("donator"))

    println(group)
    println(donator)
    println(obj.getJSONObject("groups").getString("group"))

    bank = new Bank(obj.getString("bank"))

    loginServer = obj.getJSONObject("settings").getString("loginServer")
    chatMention = obj.getJSONObject("settings").getBoolean("chatMention")
    showPlayers = obj.getJSONObject("settings").getBoolean("showPlayers")
    receiveMessages = obj.getJSONObject("settings").getBoolean("receiveMessages")

    achievements.deserialize(obj.getString("achievements"))

    punishments.loadMuteAndBan(obj.getJSONObject("punishments").getString("ban"),
      obj.getJSONObject("punishments").getString("mute"))

  }

}

object LibPlayer extends PlayerCoordinator[LibPlayer]{
  protected override def beforeUnload(uuid: UUID): Unit = {
    apply(uuid).channelStorage.subscribedChannels.foreach(c => c.leave(uuid))
  }

  protected override def afterLoad(uuid: UUID): Unit = {
    this(uuid).channelStorage.init()
  }

  override protected def genericInstance(uuid: UUID): LibPlayer = new LibPlayer(uuid)
}
