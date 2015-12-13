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

object ChannelMsg {
  val channel = MsgVal("channel")

  val error = channel.join("error")
  case class channelCantLeaveFocusWhenNoSub() extends MsgVal(error.node("cantLeaveFocusWhenNoSub"))
  case class channelNoExist(channel: String) extends MsgVal(error.node("noExist"), MsgVar("channel", channel))
  case class channelNoPermission(channel: String) extends MsgVal(error.node("noPermission"), MsgVar("channel", channel))
  case class channelErrorNoFocusPermAnymore(channel: String) extends MsgVal(error.node("noFocusPermAnymore"),
    MsgVar("channel", channel))
  case class channelErrorNoSubPermAnymore(channel: String) extends MsgVal(error.node("noSubPermAnymore"), MsgVar("channel", channel))

  val sub = channel.join("sub")
  case class channelUnsubSuccess(channel: String) extends MsgVal(sub.node("unsubSuccess"), MsgVar("channel", channel))
  case class channelSubSuccess(channel: String) extends MsgVal(sub.node("subSuccess"), MsgVar("channel", channel))
  val subList = channel.join("list")
  case class channelSubListHeader() extends MsgVal(subList.node("header"))
  case class channelSubListItem(channel: String) extends MsgVal(subList.node("item"), MsgVar("channel", channel))

  val focus = channel.join("focus")
  case class channelFocusCurrent(channel: String) extends MsgVal(focus.node("current"), MsgVar("channel", channel))
  case class channelFocusSuccess(channel: String) extends MsgVal(focus.node("success"), MsgVar("channel", channel))
  val focusError = focus.join("error")
  case class channelFocusErrorAlreadyFocused(channel: String) extends MsgVal(focusError.node("alreadyFocused"),
    MsgVar("channel", channel))

  val leave = channel.join("leave")
  case class channelLeaveSuccess(left: String, joined: String) extends MsgVal(leave.node("success"),
    MsgVar("left", left), MsgVar("joined", joined))
  val leaveError = leave.join("error")
  case class channelLeaveErrorGeneral() extends MsgVal(leaveError.node("cantLeaveGeneralWhenFocused"))
  case class channelLeaveErrorNotSubscribed(channel: String) extends MsgVal(leaveError.node("notSubscribed"),
    MsgVar("channel", channel))
  val leaveList = leave.join("list")
  case class channelLeaveListHeader() extends MsgVal(leaveError.node("header"))

  val alert = channel.join("alert")
  case class channelAlertHeader() extends MsgVal(alert.node("header"))
  case class channelAlertFooter() extends MsgVal(alert.node("footer"))
  case class channelAlertMessage(message: String) extends MsgVal(alert.node("message"), MsgVar("message", message))
  val alertError = alert.join("error")
  case class channelAlertErrorArgs() extends MsgVal(alertError.node("wrongArgs"))
}
