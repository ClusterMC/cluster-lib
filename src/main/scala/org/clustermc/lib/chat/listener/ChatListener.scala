package org.clustermc.lib.chat.listener

import org.bukkit.ChatColor
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.{EventHandler, EventPriority, Listener}
import org.clustermc.lib.chat.channel.Channel
import org.clustermc.lib.player.PlayerCoordinator
import org.clustermc.lib.utils.StringUtil
import org.clustermc.lib.utils.messages.{Messages, MsgVar}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
class ChatListener(coordinator: PlayerCoordinator) extends Listener {

    @throws[RuntimeException]
    @EventHandler(priority = EventPriority.HIGHEST)
    def asyncChat(event: AsyncPlayerChatEvent): Unit = {
        val pplayer = coordinator(event.getPlayer.getUniqueId)
        if(pplayer.muted){
            pplayer.message(Messages("punishment.youremuted", MsgVar("[TIME]", pplayer.muted))) //TODO
            event.setCancelled(true)
            return
        }
        val chatData = pplayer.channelStorage
        val focused = chatData.focusedChannel

        if(focused.isEmpty || !focused.get.canSend(event.getPlayer)) {
            chatData.setFocusedChannel(Channel.get("general"))
        }
        if(focused.isEmpty || !focused.get.canSend(event.getPlayer)) {
            event.setFormat(s"${event.getFormat.replace("{channel)}", "") }")
            return
        }

        val _focused = focused.get
        event.getFormat match {
            case s: String if s.contains("{channel}") =>
                val format = s"${StringUtil.colorString(_focused.color) }${_focused.prefixOrName }${ChatColor.RESET }"
                event.setFormat(event.getFormat.replace("{channel}", format))
            case _ => event.setFormat(s"${ChatColor.GOLD }${_focused.name }${ChatColor.RESET }${event.getFormat }")
        }

        //strip players
        val iter = event.getRecipients.iterator()
        while(iter.hasNext) {
            val p = iter.next()
            val data = coordinator(p.getUniqueId).channelStorage
            if(!data.isSubscribed(_focused)) {
                iter.remove()
            }
        }
    }
}
