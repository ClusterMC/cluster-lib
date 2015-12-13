package org.clustermc.lib.utils.messages.vals

import org.clustermc.lib.utils.messages.{MsgVal, MsgVar}

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object PunishmentMsg {
  val punishment = MsgVal("punishment")

  case class punishmentYouAreMuted(timeLeft: String) extends MsgVal(punishment.node("youAreMuted"),
    MsgVar("time", timeLeft))
  case class punishmentRecentlyPunished(timeLeft: Double) extends MsgVal(punishment.node("recentlyPunished"),
    MsgVar("time", timeLeft))

  val ban = punishment.join("ban")
  case class punishmentBanPermBanned(punisher: String, reason: String) extends MsgVal(ban.node("permBanned"),
    MsgVar("punisher", punisher), MsgVar("reason", reason))
  case class punishmentBanPermBanner(punished: String, reason: String) extends MsgVal(ban.node("permBanner"),
    MsgVar("punished", punished), MsgVar("reason", reason))
  case class punishmentBanTempBanner(punisher: String, time: String, reason: String) extends MsgVal(ban.node("tempBanned"),
    MsgVar("punisher", punisher), MsgVar("reason", reason), MsgVar("time", time))
  case class punishmentBanTempBanned(punished: String, time: String, reason: String) extends MsgVal(ban.node("tempBanner"),
    MsgVar("punished", punished), MsgVar("reason", reason), MsgVar("time", time))
  case class punishmentBanUnban(punished: String, reason: String) extends MsgVal(ban.node("unBan"),
    MsgVar("punished", punished), MsgVar("reason", reason))

  val kick = punishment.join("kick")
  case class punishmentKickKicked(punisher: String, reason: String) extends MsgVal(kick.node("kicked"),
    MsgVar("punisher", punisher), MsgVar("reason", reason))
  case class punishmentKickKicker(punished: String, reason: String) extends MsgVal(kick.node("kicker"),
    MsgVar("punished", punished), MsgVar("reason", reason))

  val warn = punishment.join("warn")
  case class punishmentWarnTitle(punisher: String, reason: String) extends MsgVal(warn.node("title"),
    MsgVar("punisher", punisher), MsgVar("reason", reason))
  case class punishmentWarnSubtitle(punisher: String, reason: String) extends MsgVal(warn.node("subtitle"),
    MsgVar("punished", punisher), MsgVar("reason", reason))
  case class punishmentWarnWarner(punished: String, reason: String) extends MsgVal(warn.node("warner"),
    MsgVar("punisher", punished), MsgVar("reason", reason))

  val mute = punishment.join("mute")
  case class punishmentMutePermMuted(punisher: String, reason: String) extends MsgVal(mute.node("permMuted"),
    MsgVar("punisher", punisher), MsgVar("reason", reason))
  case class punishmentMutePermMuter(punished: String, reason: String) extends MsgVal(mute.node("permMuter"),
    MsgVar("punished", punished), MsgVar("reason", reason))
  case class punishmentMuteTempMuted(punisher: String, time: String, reason: String) extends MsgVal(mute.node("tempMuted"),
    MsgVar("punisher", punisher), MsgVar("reason", reason), MsgVar("time", time))
  case class punishmentMuteTempMuter(punished: String, time: String, reason: String) extends MsgVal(mute.node("tempMuter"),
    MsgVar("punished", punished), MsgVar("reason", reason), MsgVar("time", time))
  case class punishmentMuteUnmuter(punished: String, reason: String) extends MsgVal(mute.node("unMuter"),
    MsgVar("punished", punished), MsgVar("reason", reason))
  case class punishmentMuteUnmuted(punisher: String, reason: String) extends MsgVal(mute.node("unMuted"),
    MsgVar("punished", punisher), MsgVar("reason", reason))

  val info = punishment.join("info")
  case class punishmentInfoHeader(player: String) extends MsgVal(info.node("header"), MsgVar("player", player))
  case class punishmentInfoNameOnline(player: String, online: Boolean) extends MsgVal(info.node("nameAndOnline"),
    MsgVar("player", player), MsgVar("online", online))
  case class punishmentInfoRank(doantor: String, perm: String) extends MsgVal(info.node("rank"),
    MsgVar("doantor", doantor), MsgVar("perm", perm))
  case class punishmentInfoBanned(reason: String, time: String) extends MsgVal(info.node("ban"),
    MsgVar("time", time), MsgVar("reason", reason))
  case class punishmentInfoMuted(reason: String, time: String) extends MsgVal(info.node("mute"),
    MsgVar("time", time), MsgVar("reason", reason))
  case class punishmentInfoNotMuted() extends MsgVal(info.node("notMuted"))
  case class punishmentInfoNotBanned() extends MsgVal(info.node("notBanned"))
  case class punishmentInfoFooter() extends MsgVal(info.node("footer"))

  val error = punishment.join("error")
  case class punishmentErrorInvalidDuration(input: String) extends MsgVal(error.node("invalidDuration"),
    MsgVar("input", input))
  case class punishmentErrorCantUseOn() extends MsgVal(error.node("cantUseOn"))
  case class punishmentErrorArgs() extends MsgVal(error.node("wrongArgs"))

}
