package org.clustermc.lib.punishment.commands.info

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.punishment.data.Punishment
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
object InfoCommand{

  def apply(context: CommandContext): Unit ={
    if(context.length != 1){
      context.sender.sendMessage(Messages("punishment.error.notEnoughArgs"))
      return
    }
    val playerVar = MsgVar("{PLAYER}", context.args(0))
    val pplayer = ClusterPlayer(context.sender.getUniqueId)
    if(pplayer.hasRank(PermissionRank.MOD)){
      val punished: Player = Bukkit.getPlayer(context.args(0))
      if(punished != null){
        showTo(context.sender, ClusterPlayer(punished.getUniqueId), true)
      }else{
        try{
          val uuid = UUIDFetcher.getUUIDOf(context.args(0))
          if(uuid != null && existsInDatabase(uuid)){
            showTo(context.sender, ClusterPlayer(uuid), false)
          }else context.sender.sendMessage(Messages("punishment.error.noExist", playerVar))
        }catch{
          case e: Exception =>
            context.sender.sendMessage(Messages("punishment.error.noExist", playerVar))
        }
      }
    }else context.sender.sendMessage(Messages("punishment.error.noPerm"))
  }

  def existsInDatabase(uuid: UUID): Boolean ={
    val amount = ClusterPlayer.collection().count(
      new Document(ClusterPlayer.index, uuid.toString),
      new CountOptions().limit(1))
    amount == 1
  }

  def showTo(sender: Player, punished: ClusterPlayer, online: Boolean): Unit ={
    sender.sendMessage(Messages("punishment.info.header", MsgVar("{PLAYER}", punished.latestName)))
    sender.sendMessage(Messages("punishment.info.nameAndOnline",
      MsgVar("{PLAYER}", punished.latestName),
      MsgVar("{ONLINE}", online.toString),
      MsgVar("{DONATOR}", punished.donator.toString),
      MsgVar("{PERM}", punished.group.toString)))
    if(punished.banned){
      val ban = Punishment.load(punished.punishments._ban.get)
      sender.sendMessage(Messages("punishment.info.ban",
        MsgVar("{ID}", ban.objectId.toString),
        MsgVar("{REASON}", ban.reason),
        MsgVar("{TIME}", if(!ban.timed){"Indefinite"}else{Punishment.timeLeft(ban.objectId).toString} )))
    }else{sender.sendMessage(Messages("punishment.info.notBanned"))}
    if(punished.muted){
      val mute = Punishment.load(punished.punishments._mute.get)
      sender.sendMessage(Messages("punishment.info.mute",
        MsgVar("{ID}", mute.objectId.toString),
        MsgVar("{REASON}", mute.reason),
        MsgVar("{TIME}", if(!mute.timed){"Indefinite"}else{Punishment.timeLeft(mute.objectId).toString} )))
    }else{sender.sendMessage(Messages("punishment.info.notMuted"))}
    sender.sendMessage(Messages("punishment.info.footer"))
  }


}
