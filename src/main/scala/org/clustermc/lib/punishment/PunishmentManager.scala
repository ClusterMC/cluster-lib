package org.clustermc.lib.punishment

import java.time.Duration
import java.util.UUID

import org.clustermc.lib.player.libplayer.LibPlayer
import org.clustermc.lib.punishment.data.Punishment
import org.clustermc.lib.utils.TitleAPI
import org.clustermc.lib.utils.messages.vals.PunishmentMsg._
import org.clustermc.lib.xserver.XMessageIdentifier
import org.clustermc.lib.xserver.messages.XPlayerMessage

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object PunishmentManager {

  def ban(punisherName: String, punisher: UUID, punished: UUID, reason: String): Unit ={
    val id = Punishment.create(PunishmentType.BAN, punisher, punished, reason).objectId
    LibPlayer.act(punished, player => {
      player.punishments._ban = Option(id)
      player.bukkitPlayer.kickPlayer(punishmentBanPermBanned(punisherName, reason).get)
    }, allowOffline = true, xPlayerAction = Option(
      PunishmentXMessage(PunishmentType.BAN, punisher, punished, reason))
    )
  }

  def tempban(time: Duration, punisherName: String, punisher: UUID, punished: UUID, reason: String): Unit ={
    val id = Punishment.create(PunishmentType.TEMPBAN, punisher, punished, reason, time).objectId
    LibPlayer.act(punished, player => {
      player.punishments._ban = Option(id)
      player.bukkitPlayer.kickPlayer(punishmentBanTempBanned(punisherName, time.toString, reason).get)
    }, allowOffline = true, xPlayerAction = Option(
      TempPunishmentXMessage(PunishmentType.TEMPBAN, time.toString, punisher, punished, reason))
    )
  }

  def unban(punisherName: String, punisher: UUID, punished: UUID, reason: String): Unit ={
    LibPlayer.act(punished, player => {
      player.punishments._ban = None
    }, allowOffline = true, xPlayerAction = Option(
      PunishmentXMessage(PunishmentType.UNBAN, punisher, punished, reason))
    )
  }


  def mute(punisherName: String, punisher: UUID, punished: UUID, reason: String): Unit ={
    val id = Punishment.create(PunishmentType.MUTE, punisher, punished, reason).objectId
    LibPlayer.act(punished, player => {
      player.punishments._mute = Option(id)
      player.message(punishmentMutePermMuted(punisherName, reason).get)
    }, allowOffline = true, xPlayerAction = Option(
      PunishmentXMessage(PunishmentType.MUTE, punisher, punished, reason))
    )
  }

  def tempmute(time: Duration, punisherName: String, punisher: UUID, punished: UUID, reason: String): Unit ={
    val id = Punishment.create(PunishmentType.TEMPMUTE, punisher, punished, reason, time).objectId
    LibPlayer.act(punished, player => {
      player.punishments._mute = Option(id)
      player.message(punishmentMuteTempMuted(punisherName, time.toString, reason).get)
    }, allowOffline = true, xPlayerAction = Option(
      TempPunishmentXMessage(PunishmentType.TEMPMUTE, time.toString, punisher, punished, reason))
    )
  }

  def unmute(punisherName: String, punisher: UUID, punished: UUID, reason: String): Unit ={
    val id = Punishment.create(PunishmentType.UNMUTE, punisher, punished, reason).objectId
    LibPlayer.act(punished, player => {
      player.punishments._mute = None
      player.message(punishmentMuteUnmuted(punisherName, reason).get)
    }, allowOffline = true, xPlayerAction = Option(
      PunishmentXMessage(PunishmentType.UNMUTE, punisher, punished, reason))
    )
  }

  def kick(punisherName: String, punisher: UUID, punished: UUID, reason: String): Unit ={
    Punishment.create(PunishmentType.KICK, punisher, punished, reason)
    LibPlayer.act(punished, player => {
        player.bukkitPlayer.kickPlayer(punishmentKickKicked(punisherName, reason).get)
    }, allowOffline = false, xPlayerAction = Option(
      PunishmentXMessage(PunishmentType.KICK, punisher, punished, reason))
    )
  }

  def warn(punisherName: String, punisher: UUID, punished: UUID, reason: String): Unit ={
    Punishment.create(PunishmentType.WARN, punisher, punished, reason)
    LibPlayer.act(punished, player => {
      val title = punishmentWarnTitle(punisherName, reason).get
      val subtitle = punishmentWarnSubtitle(punisherName, reason).get
      TitleAPI.sendTitle(player.bukkitPlayer, int2Integer(5), int2Integer(60), int2Integer(5), title, subtitle)
    }, allowOffline = false, xPlayerAction = Option(
      PunishmentXMessage(PunishmentType.WARN, punisher, punished, reason))
    )

  }

}

case class PunishmentXMessage(typ: PunishmentType, punisher: UUID, punished: UUID, reason: String)
  extends XPlayerMessage(
    "ReplacedLater",
    XMessageIdentifier.PUNISHMENT,
    typ.toString + "|" + punisher.toString + "|" + punished.toString + "|" + reason.replace("|", ""))

case class TempPunishmentXMessage(typ: PunishmentType, time: String, punisher: UUID, punished: UUID, reason: String)
  extends XPlayerMessage(
    "ReplacedLater",
    XMessageIdentifier.PUNISHMENT,
    typ.toString + "|" + time + "|" + punisher.toString + "|" + punished.toString + "|" + reason.replace("|", ""))