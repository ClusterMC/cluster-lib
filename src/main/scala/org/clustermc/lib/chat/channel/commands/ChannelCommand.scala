package org.clustermc.lib.chat.channel.commands

import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.command.PlayerCommandContext

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object ChannelCommand {

  //channel|c|chat|ch|chan -- displays help
    //sub|subscribe|join|listen|view -- lists subscribed channels
      //<channel> -- subscribes to that channel
    //focus|f|talk|t -- lists name of channel youre talking in
      //<channel> -- subscribes to and focuses on channel
    //leave|exit|x|quit|q|ex -- leaves currently focused channel and switches to global
      //<channel> -- leaves channel
    //help|h|?|what|how|wtf|halp|que -- shows the help
    //<channel> <message> -- quick send
  def apply(context: PlayerCommandContext): Unit = {
    val length = context.length
    if(length == 0) {
      ChannelHelpCommand(context.sender)
      return
    }
    context.args(0).toLowerCase match {
      case "sub" | "subscribe" | "join" | "listen" | "view" | "j" | "s" =>
        if(length == 1) SubscribeCommand(context.sender) else SubscribeCommand(context.sender, context.args(1))
      case "focus" | "f" | "talk" | "t" =>
        if(length == 1) FocusCommand(context.sender) else FocusCommand(context.sender, context.args(1))
      case "leave" | "exit" | "x" | "ex" | "q" | "quit" | "l" | "r" | "remove" =>
        if(length == 1) LeaveCommand(context.sender) else LeaveCommand(context.sender, context.args(1))
      case "help" | "h" | "?" | "what" | "how" | "idk" | "halp" | "que" => ChannelHelpCommand(context.sender)
      case "suball" | "sall" => SubscribeToAllCommand(context)
      case "alert" => AlertCommand(context)
      case name => if(Channel.channels.contains(name.toLowerCase)){
        if(length == 1) FocusCommand(context.sender, context.args(0))
        else if(length >= 3) SendCommand(context.sender, context.args(0), context.args.drop(1))
        else ChannelHelpCommand(context.sender)
      }
    }
  }

}
