package org.clustermc.lib.player.libplayer.commands.econ

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.clustermc.lib.command.PlayerCommandContext
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.utils.UUIDFetcher
import org.clustermc.lib.utils.messages.vals.EconMsg.{econErrorArgs, econErrorInvalidAmount, econErrorInvalidCurrency, econSuccess}
import org.clustermc.lib.utils.messages.vals.GeneralMsg.{generalNoPermission, generalPlayerNoExist}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object EconomyCommand {

  //eco set c/s <player> <amount>
  //eco give c/s <player> <amount>
  //eco take c/s <player> <amount>
  def apply(context: PlayerCommandContext): Unit = {
    val cplayer = LibPlayer(context.sender.getUniqueId)
    if (cplayer.hasRank(PermissionRank.NETADMIN)) {
      if (context.length != 4) {
        context.sender.sendMessage(econErrorArgs().get)
        return
      }
      val punished = Bukkit.getPlayer(context.args(2))
      if (punished != null) {
        command(context.sender, punished.getUniqueId, context.args(1), context.args(2), context.args(3).toInt, true)
      } else {
        try {
          val uuid = UUIDFetcher.getUUIDOf(context.args(2))
          if (uuid != null && existsInDatabase(uuid)) {
            command(context.sender, uuid, context.args(1), context.args(2), context.args(3).toInt, false)
          } else context.sender.sendMessage(generalPlayerNoExist(context.args(2).toLowerCase).get)
        } catch {
          case e: Exception =>
            context.sender.sendMessage(generalPlayerNoExist(context.args(2).toLowerCase).get)
        }
      }
    } else context.sender.sendMessage(generalNoPermission().get)
  }

  def command(player: Player, uuid: UUID, subcommand: String, currency: String, amount: Int, online: Boolean): Unit ={
    if(currency != "c" && currency != "s"){
      player.sendMessage(econErrorInvalidCurrency().get)
      return
    }
    if(amount <= 0 || amount >= 999999999){
      player.sendMessage(econErrorInvalidAmount().get)
      return
    }

    var exist = true
    val cplayer = LibPlayer(uuid)
    subcommand.toLowerCase match {
      case "set" =>
        if(currency == "c"){ cplayer.bank.getClusterWallet.setAmount(amount)}
        else if(currency == "s"){ cplayer.bank.getShardWallet.setAmount(amount)}
      case "give" =>
        if(currency == "c"){ cplayer.bank.getClusterWallet.deposit(amount)}
        else if(currency == "s"){ cplayer.bank.getShardWallet.deposit(amount)}
      case "take" =>
        if(currency == "c"){ cplayer.bank.getClusterWallet.withdraw(amount)}
        else if(currency == "s"){ cplayer.bank.getShardWallet.withdraw(amount)}
      case test => exist = false
    }
    if(exist){
      player.sendMessage(econSuccess(subcommand.toLowerCase, cplayer.latestName, amount, cplayer.bank.serialize()).get)
      if(!online) LibPlayer.unload(cplayer.itemId)
    }

  }

  def existsInDatabase(uuid: UUID): Boolean = {
    val amount = LibPlayer.collection().count(
      new Document(LibPlayer.index, uuid.toString),
      new CountOptions().limit(1))
    amount == 1
  }

}
