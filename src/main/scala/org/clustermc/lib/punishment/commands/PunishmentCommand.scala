package org.clustermc.lib.punishment.commands

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.utils.UUIDFetcher
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

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
  val name: String
  val color: String
  val needsOnline: Boolean
  val permRequired: PermissionRank

  def apply(context: CommandContext): Unit ={
    if(context.length < minArgLength){
      context.sender.sendMessage(Messages("punishment.error.notEnoughArgs"))
      return
    }
    val playerVar = MsgVar("{PLAYER}", context.args(0))
    val pplayer = ClusterPlayer(context.sender.getUniqueId)
    val reason = context.args.drop(minArgLength - 1).mkString(" ")
    if(pplayer.hasRank(permRequired)){
      val punished: Player = Bukkit.getPlayer(context.args(0))
      if(punished != null){
        act(ClusterPlayer(punished.getUniqueId), pplayer, context.sender)
        punish(ClusterPlayer(punished.getUniqueId), pplayer, context.sender, punished, reason, online = true, context.args)
      }else if(needsOnline){
        context.sender.sendMessage(Messages("general.notOnline"))
      }else{
        try{
          val uuid = UUIDFetcher.getUUIDOf(context.args(0))
          if(uuid != null && existsInDatabase(uuid)){
            act(ClusterPlayer(uuid), pplayer, context.sender)
            punish(ClusterPlayer(uuid), pplayer, context.sender, punished, reason, online = false, context.args)
          }else context.sender.sendMessage(Messages("general.playerNoExist", playerVar))
        }catch{
          case e: Exception =>
            context.sender.sendMessage(Messages("general.playerNoExist", playerVar))
        }
      }

    }else context.sender.sendMessage(Messages("general.noPermission"))
  }

  def existsInDatabase(uuid: UUID): Boolean ={
    val amount = ClusterPlayer.collection().count(
      new Document(ClusterPlayer.index, uuid.toString),
      new CountOptions().limit(1))
    amount == 1
  }

  def act(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player): Unit ={
    if(ppunished.hasRank(PermissionRank.MOD) && !pplayer.hasRank(PermissionRank.NETADMIN)) {
      punisher.sendMessage(Messages("punishment.error.cantUseOn"))
      return
    }
    if(!pplayer.hasRank(PermissionRank.NETADMIN)) {
      if(ClusterLib.instance.cooldowns.isCooling(ppunished.itemId, "punished")) {
        punisher.sendMessage(Messages("punishment.error.recentPunished",
          MsgVar("{TIME}", ClusterLib.instance.cooldowns.get(ppunished.itemId).timeRemaining("punished"))))
        return
      }
    }
    ClusterLib.instance.cooldowns.add(ppunished.itemId, "punished", 10)
  }

  def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player,
             punished: Player, reason: String, online: Boolean, args: Array[String]): Unit

}
