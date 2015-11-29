package org.clustermc.lib.player.commands.econ

import java.util.UUID

import com.mongodb.client.model.CountOptions
import org.bson.Document
import org.bukkit.Bukkit
import org.bukkit.entity.Player
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

object EconomyCommand {

  //eco set c/s <player> <amount>
  //eco give c/s <player> <amount>
  //eco take c/s <player> <amount>
  def apply(context: CommandContext): Unit = {
    val cplayer = ClusterPlayer(context.sender.getUniqueId)
    if (cplayer.hasRank(PermissionRank.NETADMIN)) {
      if (context.length != 4) {
        context.sender.sendMessage(Messages("econ.error.wrongArgs"))
        return
      }
      val playerVar = MsgVar("{PLAYER}", context.args(2))
      val punished = Bukkit.getPlayer(context.args(2))
      if (punished != null) {
        command(context.sender, punished.getUniqueId, context.args(1), context.args(2), context.args(3).toInt, true)
      } else {
        try {
          val uuid = UUIDFetcher.getUUIDOf(context.args(2))
          if (uuid != null && existsInDatabase(uuid)) {
            command(context.sender, uuid, context.args(1), context.args(2), context.args(3).toInt, false)
          } else context.sender.sendMessage(Messages("general.playerNoExist", playerVar))
        } catch {
          case e: Exception =>
            context.sender.sendMessage(Messages("general.playerNoExist", playerVar))
        }
      }
    } else context.sender.sendMessage(Messages("general.noPermission"))
  }

  def command(player: Player, uuid: UUID, subcommand: String, currency: String, amount: Int, online: Boolean): Unit ={
    if(currency != "c" && currency != "s"){
      player.sendMessage(Messages("econ.error.invalidCurrency"))
      return
    }
    if(amount <= 0 || amount >= 999999999){
      player.sendMessage(Messages("econ.error.invalidAmount"))
      return
    }

    var exist = true
    val cplayer = ClusterPlayer(uuid)
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
      player.sendMessage(Messages("econ.success." + subcommand.toLowerCase,
        MsgVar("{PLAYER}", cplayer.latestName),
        MsgVar("{AMOUNT}", amount),
        MsgVar("{BALANCE}", cplayer.bank.serialize())))
      if(!online) ClusterPlayer.unload(cplayer.itemId)
    }

  }

  def existsInDatabase(uuid: UUID): Boolean = {
    val amount = ClusterPlayer.collection().count(
      new Document(ClusterPlayer.index, uuid.toString),
      new CountOptions().limit(1))
    amount == 1
  }

}
