package org.clustermc.lib

import org.bukkit.command.{Command, CommandExecutor, CommandSender}
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import org.clustermc.lib.achievements.LocationAchievementRunnable
import org.clustermc.lib.chat.announcer.Announcer
import org.clustermc.lib.chat.channel.commands.ChannelCommand
import org.clustermc.lib.chat.listener.ChatListener
import org.clustermc.lib.chat.privatemessage.WhisperCommand
import org.clustermc.lib.command.CommandContext
import org.clustermc.lib.player.ClusterPlayer
import org.clustermc.lib.player.commands.econ.EconomyCommand
import org.clustermc.lib.player.commands.rank.RankCommand
import org.clustermc.lib.player.event.PlayerIO
import org.clustermc.lib.punishment.commands.ban.{BanCommand, TimedBanCommand, UnbanCommand}
import org.clustermc.lib.punishment.commands.info.InfoCommand
import org.clustermc.lib.punishment.commands.misc.{KickCommand, WarnCommand}
import org.clustermc.lib.punishment.commands.mute.{MuteCommand, TimedMuteCommand, UnmuteCommand}
import org.clustermc.lib.utils.cooldown.CooldownHandler
import org.clustermc.lib.utils.database.Mongo
import org.clustermc.lib.utils.{ClusterServerPlugin, CustomConfig}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class ClusterLib extends ClusterServerPlugin("lib") with CommandExecutor{

    private var _mongoDB: Mongo = null
    private var _cooldowns: CooldownHandler = null

    private var cooldownTask: BukkitTask = null

    def database = _mongoDB

    def cooldowns = _cooldowns

    override def onEnable(): Unit = {
        ClusterLib._instance = this
        _cooldowns = new CooldownHandler

        _mongoDB = new Mongo(new CustomConfig(this.getDataFolder, "db").getConfig)
        _mongoDB.open()

        new ChatListener
        new PlayerIO
        Announcer.start()
        LocationAchievementRunnable.start()

        Array("rank", "ban", "unban", "mute", "unmute", "tmute", "info", "tban", "kick", "warn", "eco", "whisper", "channel")
          .foreach(s => getCommand(s).setExecutor(this))

        cooldownTask = getServer.getScheduler.runTaskTimerAsynchronously(this, new Runnable {
            override def run(): Unit = _cooldowns.handleCooldowns()
        }, 20L, 5L)
    }

    override def onDisable(): Unit = {
        LocationAchievementRunnable.end()
        cooldownTask.cancel()
        ClusterPlayer.unloadAll()
        Announcer.end()
        ClusterLib._instance = null
        _mongoDB.getClient.close()
    }

    override def onCommand(sender: CommandSender, cmd: Command, label: String, args: Array[String]): Boolean ={
        sender match {
            case player: Player =>
                val context = new CommandContext(player, cmd.getName.toLowerCase, args)
                cmd.getName.toLowerCase match {
                    case "rank" => RankCommand(context)
                    case "ban" => BanCommand(context)
                    case "unban" => UnbanCommand(context)
                    case "mute" => MuteCommand(context)
                    case "unmute" => UnmuteCommand(context)
                    case "tmute" => TimedMuteCommand(context)
                    case "tban" => TimedBanCommand(context)
                    case "info" => InfoCommand(context)
                    case "kick" => KickCommand(context)
                    case "warn" => WarnCommand(context)
                    case "eco" => EconomyCommand(context)
                    case "whisper"|"tell"|"t"|"w"|"msg"|"m"|"r"|"re"|"reply"|"message" => WhisperCommand(context)
                    case "channel"|"c"|"chat"|"ch"|"chan" => ChannelCommand(context)
                }
            case _ =>
        }
    false
    }

}

object ClusterLib {
    private var _instance: ClusterLib = null
    def instance: ClusterLib = _instance
}
