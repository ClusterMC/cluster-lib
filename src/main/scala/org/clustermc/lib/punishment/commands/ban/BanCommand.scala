package org.clustermc.lib.punishment.commands.ban

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.player.storage.{ClusterPlayer, PlayerCoordinator}
import org.clustermc.lib.punishment.PunishmentType
import org.clustermc.lib.punishment.data.Punishment
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

object BanCommand {

  //ban <player> <reason>
  def apply(context: CommandContext): Unit = {
    if(context.length < 2){
      context.sender.sendMessage(Messages("punishment.ban.error.notEnoughArgs"))
      return
    }
    if(true){
      //TODO DO REGEX FOR PLAYERNAMES
    }
    val pplayer = PlayerCoordinator(context.sender.getUniqueId)
    if(pplayer.hasRank(PermGroup.ADMIN)){
      val punished: Player = Bukkit.getPlayer(context.args(0))
      if(punished != null){
        if(act(PlayerCoordinator(punished.getUniqueId), pplayer, context)){
          punished.kickPlayer(Messages("punishment.ban.gotPermBanned",
            MsgVar("{PUNISHER}", context.sender.getName),
            MsgVar("{REASON}", context.args.drop(0).mkString(" "))))
        }
      }else{
        try{
          val uuid = UUIDFetcher.getUUIDOf(context.args(0))
          val amount: Long = ClusterLib.instance.database.getDatabase("data").getCollection(PlayerCoordinator.collection)
            .count(new Document(PlayerCoordinator.index, uuid.toString), new CountOptions().limit(1))
          if(amount == 1) act(PlayerCoordinator(uuid), pplayer, context)
          else context.sender.sendMessage(Messages("punishment.ban.error.noExist", MsgVar("{PLAYER}", context.args(0))))
        }catch{
          case e: Exception =>
            context.sender.sendMessage(Messages("punishment.ban.error.noExist", MsgVar("{PLAYER}", context.args(0))))
        }
      }
    }else context.sender.sendMessage(Messages("punishment.ban.error.noPerms"))
  }

  def act(ppunished: ClusterPlayer, pplayer: ClusterPlayer, context: CommandContext): Boolean ={
    if(!ppunished.hasRank(PermGroup.MOD)){
      if(pplayer.hasRank(PermGroup.NETADMIN) || ClusterLib.instance.cooldowns.isCooling(ppunished.itemId, "punished")){
        ClusterLib.instance.cooldowns.add(ppunished.itemId, "punished", 10)
        val reason = context.args.drop(0).mkString(" ")
        ppunished.punishments._ban = Option(
          Punishment.create(
            PunishmentType.BAN, context.sender.getUniqueId, ppunished.itemId, reason
          ).objectId)
        context.sender.sendMessage(Messages("punishment.ban.youPermBanned",
          MsgVar("{REASON}", reason),
          MsgVar("{PUNISHED}", ppunished.latestName)))
        return true
      }else context.sender.sendMessage(Messages("punishment.ban.error.recentlyPunished",
        MsgVar("{TIME}", ClusterLib.instance.cooldowns.get(ppunished.itemId).timeRemaining("punished"))))
    }else context.sender.sendMessage(Messages("punishment.ban.error.cantBeBanned"))
    false
  }
}
