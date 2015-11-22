package org.clustermc.lib.command

import org.bukkit.entity.Player

class CommandContext(val sender: Player, val command: String,
                     val args: Array[String], cancelled: Boolean = false) {

}
