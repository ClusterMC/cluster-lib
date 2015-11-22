package org.clustermc.lib.chat.channel.commands

import org.clustermc.lib.command.CommandContext

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class ChannelCommand {

  //channel|c|chat|ch|chan -- displays help
    //sub|subscribe|join|listen|view -- lists subscribed channels
      //<channel> -- subscribes to that channel
    //focus|f|talk|t -- lists name of channel youre talking in
      //<channel> -- subscribes to and focuses on channel
    //leave|exit|x|quit|q|ex -- leaves currently focused channel and switches to global
      //<channel> -- leaves channel
    //help|h|?|what|how|wtf|halp|que -- shows the help
  val channelBase = (context: CommandContext) => {

  }

}
