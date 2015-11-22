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
class ChatListener extends Listener {

    @throws[RuntimeException]
    @EventHandler(priority = EventPriority.HIGHEST)
    def asyncChat(event: AsyncPlayerChatEvent): Unit = {
        val pplayer = PlayerCoordinator(event.getPlayer.getUniqueId)
        if(pplayer.muted){
            pplayer.message(Messages("punishment.youremuted", MsgVar("[TIME]", pplayer.muted))) //TODO
            event.setCancelled(true)
            return
        }
        val chatData = pplayer.channelStorage
        val focused = chatData.focusedChannel

        if(!focused.canSend(event.getPlayer)) {
            chatData.focus(Channel.get("general").get)
            event.setFormat(s"${event.getFormat.replace("{NAME)}", "") }")
            return
        }

        event.getFormat match {
            case s: String if s.contains("{NAME}") =>
                val format = s"${StringUtil.colorString(focused.color) }${focused.prefixOrName }${ChatColor.RESET }"
                event.setFormat(event.getFormat.replace("{NAME}", format))
            case _ => event.setFormat(s"${ChatColor.GOLD }${focused.name }${ChatColor.RESET }${event.getFormat }")
        }

        //strip players
        val iter = event.getRecipients.iterator()
        while(iter.hasNext) {
            val p = iter.next()
            val data = PlayerCoordinator(p.getUniqueId).channelStorage
            if(!data.isSubscribed(focused)) {
                iter.remove()
            }
        }
    }
}
