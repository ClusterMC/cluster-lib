package org.clustermc.lib.player.commands.rank

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.enums.{DonatorRank, PermissionRank}
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

object RankCommand {

  //rank <player> <rank>
  def apply(context: CommandContext): Unit = {
    val cplayer = ClusterPlayer(context.sender.getUniqueId)
    if (cplayer.hasRank(PermissionRank.NETADMIN) || context.sender.isOp) {
      if (context.length != 2) {
        context.sender.sendMessage(Messages("rank.error.wrongArgs"))
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
          } else context.sender.sendMessage(Messages("general.playerNoExist", MsgVar("{PLAYER}", context.args(0))))
        } catch {
          case e: Exception =>
            context.sender.sendMessage(Messages("general.playerNoExist", MsgVar("{PLAYER}", context.args(0))))
        }
      }
    } else context.sender.sendMessage(Messages("general.noPermission"))
  }

  def command(player: Player, uuid: UUID, rankString: String, online: Boolean): Unit ={
    val cplayer = ClusterPlayer(uuid)
    if(DonatorRank.contains(rankString)){
      cplayer.donator = DonatorRank.valueOf(rankString.toUpperCase)
    }else if(PermissionRank.contains(rankString)){
      cplayer.group = PermissionRank.valueOf(rankString.toUpperCase)
    }else{
      player.sendMessage(Messages("rank.error.invalidRank", MsgVar("{INPUT}", rankString.toUpperCase)))
      return
    }
    player.sendMessage(Messages("rank.success", MsgVar("{PLAYER}", cplayer.latestName)))
    if(!online) ClusterPlayer.unload(uuid)
  }

  def existsInDatabase(uuid: UUID): Boolean = {
    val amount = ClusterPlayer.collection().count(
      new Document(ClusterPlayer.index, uuid.toString),
      new CountOptions().limit(1))
    amount == 1
  }

}
