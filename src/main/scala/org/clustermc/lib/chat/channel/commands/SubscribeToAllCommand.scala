package org.clustermc.lib.chat.channel.commands

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

object SubscribeToAllCommand {

  //ch suball
  def apply(context: CommandContext): Unit ={
    val cplayer = ClusterPlayer(context.sender.getUniqueId)
    if(cplayer.hasRank(PermissionRank.MOD)){
      Channel.channels.values.foreach(c => cplayer.channelStorage.subscribe(c))
    }else context.sender.sendMessage(Messages("general.noPermission"))
  }

}
