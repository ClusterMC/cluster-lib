package org.clustermc.lib.punishment.commands

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.utils.UUIDFetcher
import org.clustermc.lib.utils.messages.vals.GeneralMsg.{generalNoPermission, generalNotOnline, generalPlayerNoExist}
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.{punishmentErrorArgs, punishmentErrorCantUseOn, punishmentRecentlyPunished}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

trait PunishmentCommand {
  val minArgLength: Int
  val color: String
  val needsOnline: Boolean
  val permRequired: PermissionRank

  def apply(context: CommandContext): Unit ={
    if(context.length < minArgLength){
      context.sender.sendMessage(punishmentErrorArgs().get)
      return
    }
    val pplayer = LibPlayer(context.sender.getUniqueId)
    val reason = context.args.drop(minArgLength - 1).mkString(" ")
    if(pplayer.hasRank(permRequired)){
      val punished: Player = Bukkit.getPlayer(context.args(0))
      if(punished != null){
        act(LibPlayer(punished.getUniqueId), pplayer, context.sender)
        punish(LibPlayer(punished.getUniqueId), pplayer, context.sender, punished, reason, online = true, context.args)
      }else if(needsOnline){
        context.sender.sendMessage(generalNotOnline().get)
      }else{
        try{
          val uuid = UUIDFetcher.getUUIDOf(context.args(0))
          if(uuid != null && existsInDatabase(uuid)){
            act(LibPlayer(uuid), pplayer, context.sender)
            punish(LibPlayer(uuid), pplayer, context.sender, punished, reason, online = false, context.args)
          }else context.sender.sendMessage(generalPlayerNoExist(context.args(0).toLowerCase).get)
        }catch{
          case e: Exception =>
            context.sender.sendMessage(generalPlayerNoExist(context.args(0).toLowerCase).get)
        }
      }
    }else context.sender.sendMessage(generalNoPermission().get)
  }

  def existsInDatabase(uuid: UUID): Boolean ={
    val amount = LibPlayer.collection().count(
      new Document(LibPlayer.index, uuid.toString),
      new CountOptions().limit(1))
    amount == 1
  }

  def act(ppunished: LibPlayer, pplayer: LibPlayer, punisher: Player): Unit ={
    if(ppunished.hasRank(PermissionRank.MOD) && !pplayer.hasRank(PermissionRank.NETADMIN)) {
      punisher.sendMessage(punishmentErrorCantUseOn().get)
      return
    }
    if(!pplayer.hasRank(PermissionRank.NETADMIN)) {
      if(ClusterLib.instance.cooldowns.isCooling(ppunished.itemId, "punished")) {
        punisher.sendMessage(
          punishmentRecentlyPunished(ClusterLib.instance.cooldowns.get(ppunished.itemId).timeRemaining("punished")).get)
        return
      }
    }
    ClusterLib.instance.cooldowns.add(ppunished.itemId, "punished", 10)
  }

  def punish(ppunished: LibPlayer, pplayer: LibPlayer, punisher: Player,
             punished: Player, reason: String, online: Boolean, args: Array[String]): Unit

}
