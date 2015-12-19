package org.clustermc.lib.command

import org.bukkit.Bukkit
import org.bukkit.command.{CommandSender, ConsoleCommandSender}
import org.bukkit.entity.Player

case class CommandContext[T <: CommandSender](sender: T, command: String, args: Array[String], cancelled: Boolean = false) {
  lazy val length = args.length
}
case class ConsoleCommandContext(override val command: String,
                                 override val args: Array[String],
                                 override val cancelled: Boolean = false)
  extends CommandContext[ConsoleCommandSender](Bukkit.getConsoleSender, command, args , cancelled){
}
case class PlayerCommandContext(override val sender: Player,
                                override val command: String,
                                override val args: Array[String],
                                override val cancelled: Boolean = false)
  extends CommandContext[Player](sender, command, args , cancelled){
}
