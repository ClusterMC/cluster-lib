package org.clustermc.lib.command

import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.{EventHandler, Listener}
import org.bukkit.plugin.Plugin

//This is a temp solution so that commands don't get overly messy.
//Will be converted to a more fluent and extensible solution later
sealed class CommandCoordinator private(plugin: Plugin) extends Listener {
    plugin.getServer.getPluginManager.registerEvents(this, plugin)

    @EventHandler
    def preprocessCmd(event: PlayerCommandPreprocessEvent): Unit = {
        val msg = event.getMessage.substring(1)
        val split = msg.split(" ")
        val command = split(0)
        var args: Array[String] = Array()
        if(split.length >= 2) args = split.slice(1, split.length - 1)
        val context = CommandContext(event.getPlayer, command, args)



        if(context.cancelled) event.setCancelled(true)
    }
}

object CommandCoordinator {


}
