package org.clustermc.lib.player.libplayer.commands.rank

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.command.PlayerCommandContext
import org.clustermc.lib.enums.{DonatorRank, PermissionRank}
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.utils.UUIDFetcher
import org.clustermc.lib.utils.messages.vals.GeneralMsg.{generalNoPermission, generalPlayerNoExist}
import org.clustermc.lib.utils.messages.vals.RankMsg.{rankErrorArgs, rankErrorInvalidRank, rankSuccess}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object RankCommand {

  //rank <player> <rank>
  def apply(context: PlayerCommandContext): Unit = {
    val cplayer = LibPlayer(context.sender.getUniqueId)
    if (cplayer.hasRank(PermissionRank.NETADMIN) || context.sender.isOp) {
      if (context.length != 2) {
        context.sender.sendMessage(rankErrorArgs().get)
        return
      }
      val punished = Bukkit.getPlayer(context.args(0))
      if (punished != null) {
        command(context.sender, punished.getUniqueId, context.args(1), online = true)
      } else {
        try {
          val uuid = UUIDFetcher.getUUIDOf(context.args(0))
          if (uuid != null && existsInDatabase(uuid)) {
            command(context.sender, punished.getUniqueId, context.args(1), online = false)
          } else context.sender.sendMessage(generalPlayerNoExist(context.args(0).toLowerCase).get)
        } catch {
          case e: Exception =>
            context.sender.sendMessage(generalPlayerNoExist(context.args(0).toLowerCase).get)
        }
      }
    } else context.sender.sendMessage(generalNoPermission().get)
  }

  def command(player: Player, uuid: UUID, rankString: String, online: Boolean): Unit ={
    val cplayer = LibPlayer(uuid)
    if(DonatorRank.contains(rankString)){
      cplayer.donator = DonatorRank.valueOf(rankString.toUpperCase)
    }else if(PermissionRank.contains(rankString)){
      cplayer.group = PermissionRank.valueOf(rankString.toUpperCase)
    }else{
      player.sendMessage(rankErrorInvalidRank(rankString.toUpperCase).get)
      return
    }
    player.sendMessage(rankSuccess(rankString.toUpperCase).get)
    if(!online) LibPlayer.unload(uuid)
  }

  def existsInDatabase(uuid: UUID): Boolean = {
    val amount = LibPlayer.collection().count(
      new Document(LibPlayer.index, uuid.toString),
      new CountOptions().limit(1))
    amount == 1
  }

}
