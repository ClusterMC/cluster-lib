package org.clustermc.lib.chat.privatemessage

import org.bukkit.Bukkit
import org.clustermc.lib.chat.ColorFilter
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.messages.vals.GeneralMsg.generalPlayerNoExist
import org.clustermc.lib.utils.messages.vals.PunishmentMsg.punishmentYouAreMuted
import org.clustermc.lib.utils.messages.vals.WhisperMsg.{whisperErrorTurnOffOther, whisperErrorTurnOffSelf, whisperFormatReceiver, whisperFormatSender}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 *
 * This file is part of Hub.
 *
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object WhisperCommand {

  //whisper|tell|t|w|msg|m|r|re|reply|message <player name> <message>
  def apply(context: CommandContext): Unit = {
    val pplayer= ClusterPlayer(context.sender.getUniqueId)
    if(!pplayer.muted){
      val player = Bukkit.getPlayer(context.args(0))
      if (player != null) {
        if (pplayer.receiveMessages || pplayer.hasRank(PermissionRank.NETADMIN)){
          if (ClusterPlayer(player.getUniqueId).receiveMessages || pplayer.hasRank(PermissionRank.MOD)){
            val sentence = ColorFilter.filter(pplayer, context.args.drop(1).mkString(" "))
            context.sender.sendMessage(whisperFormatSender(player.getName, sentence).get)
            player.sendMessage(whisperFormatReceiver(context.sender.getName, sentence).get)
          } else context.sender.sendMessage(whisperErrorTurnOffOther().get)
        } else context.sender.sendMessage(whisperErrorTurnOffSelf().get)
      } else context.sender.sendMessage(generalPlayerNoExist(context.args(0).toLowerCase).get)
    }else pplayer.message(punishmentYouAreMuted(Punishment.timeLeft(pplayer.punishments._mute.get).get.toString).get)
  }
}
