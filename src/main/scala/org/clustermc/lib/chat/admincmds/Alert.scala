package org.clustermc.lib.chat.admincmds

import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.utils.messages.Messages

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object Alert {

  //alert <message>
  def apply(context: CommandContext): Unit ={
    val cplayer = ClusterPlayer(context.sender.getUniqueId)
    if(cplayer.hasRank(PermissionRank.NETADMIN)){
      if(context.length < 1){
        context.sender.sendMessage(Messages("channel.alert.error.notEnoughArgs"))
        return
      }
      Channel.serverAlert(context.args.mkString(" "))
    }else context.sender.sendMessage(Messages("general.noPermission"))
  }

}
