package org.clustermc.lib.punishment.commands

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.player.storage.{ClusterPlayer, PlayerCoordinator}
import org.clustermc.lib.utils.UUIDFetcher
import org.clustermc.lib.utils.messages.{Messages, MsgVar}
import org.clustermc.lib.{ClusterLib, PermGroup}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class PunishmentCommand {
  val minArgLength: Int
  val name: String
  val needsOnline: Boolean
  val permRequired: PermGroup
  val msgPrefix: String = "punishment." + name + "."

  def apply(context: CommandContext): Unit ={
    if(context.length < minArgLength){
      context.sender.sendMessage(Messages(msgPrefix + "error.notEnoughArgs"))
      return
    }
    val playerVar = MsgVar("{PLAYER}", context.args(0))
    val pplayer = PlayerCoordinator(context.sender.getUniqueId)
    val reason = context.args.drop(minArgLength - 1).mkString(" ")
    if(pplayer.hasRank(permRequired)){
      val punished: Player = Bukkit.getPlayer(context.args(0))
      if(punished != null){
        act(PlayerCoordinator(punished.getUniqueId), pplayer, context.sender, reason)
      }else if(needsOnline){
        context.sender.sendMessage(Messages(msgPrefix + "error.notOnline"))
      }else{
        try{
          val uuid = UUIDFetcher.getUUIDOf(context.args(0))
          if(uuid != null && existsInDatabase(uuid)){
            act(PlayerCoordinator(uuid), pplayer, context.sender, reason)
          }else context.sender.sendMessage(Messages(msgPrefix + "error.noExist", playerVar))
        }catch{
          case e: Exception =>
            context.sender.sendMessage(Messages(msgPrefix + "error.noExist", playerVar))
        }
      }

    }else context.sender.sendMessage(Messages("punishment." + name + ".error.noPerms"))
  }

  def existsInDatabase(uuid: UUID): Boolean ={
    val amount = ClusterLib.instance.database
      .getDatabase("data").getCollection(PlayerCoordinator.collection)
      .count(new Document(
        PlayerCoordinator.index, uuid.toString),
        new CountOptions().limit(1))
    amount == 1
  }

  def act(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, reason: String): Unit ={
    if(ppunished.hasRank(PermGroup.MOD) && !pplayer.hasRank(PermGroup.NETADMIN)) {
      punisher.sendMessage(Messages(msgPrefix + "error.cantBeBanned"))
      return
    }
    if(!pplayer.hasRank(PermGroup.NETADMIN)) {
      if(ClusterLib.instance.cooldowns.isCooling(ppunished.itemId, "punished")) {
        punisher.sendMessage(Messages(msgPrefix + "error.recentlyPunished",
          MsgVar("{TIME}", ClusterLib.instance.cooldowns.get(ppunished.itemId).timeRemaining("punished"))))
        return
      }
    }
    ClusterLib.instance.cooldowns.add(ppunished.itemId, "punished", 10)
    punish(ppunished, pplayer, punisher, reason)
  }

  def punish(ppunished: ClusterPlayer, pplayer: ClusterPlayer, punisher: Player, reason: String): Unit

}
