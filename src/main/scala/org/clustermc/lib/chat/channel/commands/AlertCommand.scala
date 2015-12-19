package org.clustermc.lib.chat.channel.commands

import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.utils.messages.vals.ChannelMsg.channelAlertErrorArgs
import org.clustermc.lib.utils.messages.vals.GeneralMsg.generalNoPermission

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object AlertCommand {

  //ch alert <message>
  def apply(context: CommandContext): Unit ={
    val cplayer = LibPlayer(context.sender.getUniqueId)
    if(cplayer.hasRank(PermissionRank.NETADMIN)){
      if(context.length < 2){
        context.sender.sendMessage(channelAlertErrorArgs().get)
        return
      }
      Channel.serverAlert(context.args.drop(0).mkString(" "))
    }else context.sender.sendMessage(generalNoPermission().get)
  }

}
