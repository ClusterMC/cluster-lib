package org.clustermc.lib.command

import org.bukkit.Bukkit
import org.bukkit.command.{CommandSender, ConsoleCommandSender}
import org.bukkit.entity.Player
import org.clustermc.lib.enums.PermissionRank
import org.clustermc.lib.player.libplayer.LibPlayer

abstract class CommandContext[T <: CommandSender](val sender: T, val command: String,
                                                  val args: Array[String], val cancelled: Boolean = false) {
  lazy val length = args.length
  def hasPermission(permissionRank: PermissionRank): Boolean

}


case class ConsoleCommandContext(override val command: String, override val args: Array[String],
                                 override val cancelled: Boolean = false)
  extends CommandContext[ConsoleCommandSender](Bukkit.getConsoleSender, command, args , cancelled){

  override def hasPermission(permissionRank: PermissionRank): Boolean = {
    true
  }

}


case class PlayerCommandContext(override val sender: Player, override val command: String,
                                override val args: Array[String], override val cancelled: Boolean = false)
  extends CommandContext[Player](sender, command, args , cancelled){

  override def hasPermission(permissionRank: PermissionRank): Boolean = {
    LibPlayer(sender.getUniqueId).hasRank(permissionRank)
  }

}
