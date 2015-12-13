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
import org.clustermc.lib.utils.messages.vals.GeneralMsg.{generalNoPermission, generalPlayerNoExist}
import org.clustermc.lib.utils.messages.vals.PunishmentMsg._

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
      context.sender.sendMessage(punishmentErrorArgs().get)
      return
    }
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
          }else context.sender.sendMessage(generalPlayerNoExist(context.args(0).toLowerCase).get)
        }catch{
          case e: Exception =>
            context.sender.sendMessage(generalPlayerNoExist(context.args(0).toLowerCase).get)
        }
      }
    }else context.sender.sendMessage(generalNoPermission().get)
  }

  def existsInDatabase(uuid: UUID): Boolean ={
    val amount = ClusterPlayer.collection().count(
      new Document(ClusterPlayer.index, uuid.toString),
      new CountOptions().limit(1))
    amount == 1
  }

  def showTo(sender: Player, punished: ClusterPlayer, online: Boolean): Unit ={
    sender.sendMessage(punishmentInfoHeader(punished.latestName).get)
    sender.sendMessage(punishmentInfoNameOnline(punished.latestName, online).get)
    sender.sendMessage(punishmentInfoRank(punished.donator.name(), punished.group.name()).get)
    if(punished.banned){
      val ban = Punishment.load(punished.punishments._ban.get)
      sender.sendMessage(punishmentInfoBanned(ban.reason,
        if(!ban.timed) "Indefinite" else Punishment.timeLeft(ban.objectId).get.toString).get)
    }else{sender.sendMessage(punishmentInfoNotBanned().get)}
    if(punished.muted){
      val mute = Punishment.load(punished.punishments._mute.get)
      sender.sendMessage(punishmentInfoMuted(mute.reason,
        if(!mute.timed) "Indefinite" else Punishment.timeLeft(mute.objectId).get.toString).get)
    }else{sender.sendMessage(punishmentInfoNotMuted().get)}
    sender.sendMessage(punishmentInfoFooter().get)
  }

}
