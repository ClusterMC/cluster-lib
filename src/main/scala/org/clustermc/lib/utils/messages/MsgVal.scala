package org.clustermc.lib.utils.messages

case class MsgVal(key: String, parameters: List[MsgVar]) {

    def this(key: String, parameters: MsgVar*) =
        this(key, parameters)

    def node(leaf: String): String =
        key + "." + leaf

    def join(msg: MsgVal, withParam: Boolean = false): MsgVal =
        new MsgVal(key + "." + msg.key, if(withParam) msg.parameters ::: parameters.toList else Nil)

    def join(msg: String, withParam: Boolean = false): MsgVal =
        new MsgVal(key + "." + msg, if(withParam) parameters else Nil)
}

object MsgVal {

    def apply(key: String, parameters: MsgVar*) = apply(key, parameters.toList)

    def apply(key: String) = apply(key, Nil)

    def apply(key: String, parameters: List[MsgVar]): MsgVal = new MsgVal(key, parameters)

    val rank = MsgVal("rank")
    val rankError = rank.join("error")

    case class rankErrorArgs() extends MsgVal(rankError.node("wrongArgs"))

    val serverSelector = MsgVal("serverSelector")
    val serverSelectorError = serverSelector.join("error")

    case class serverSelectorErrorWaitToUse() extends MsgVal(serverSelectorError.node("waitToUse"))

    val lottery = MsgVal("lottery")
    val lotteryError = lottery.join("error")

    case class lotteryNotEnough() extends MsgVal(lotteryError.node("notEnough"))

    case class lotteryOnlyNoneStaff() extends MsgVal(lotteryError.node("onlyNonStaff"))

    case class lotteryCantEnterZero() extends MsgVal(lotteryError.node("cantEnterZero"))

    case class lotteryAlreadyEnrolled() extends MsgVal(lotteryError.node("alreadyEnrolled"))

    val lotteryShard = lottery.join("shard")

    case class lotteryShardHeader() extends MsgVal(lotteryShard.node("header"))

    val lotteryCluster = lottery.join("cluster")

    case class lotteryClusterHeader() extends MsgVal(lotteryCluster.node("header"))

    val punishment = MsgVal("punishment")
    val punishmentInfo = punishment.join("info")

    case class punishmentInfoNotMuted() extends MsgVal(punishmentInfo.node("notMuted"))

    case class punishmentInfoNotBanned() extends MsgVal(punishmentInfo.node("notBanned"))

    case class punishmentInfoFooter() extends MsgVal(punishmentInfo.node("footer"))

    val punishmentError = punishment.join("error")

    case class punishmentErrorCantUseOn() extends MsgVal(punishmentError.node("cantUseOn"))

    case class punishmentErrorArgs() extends MsgVal(punishmentError.node("wrongArgs"))

    val message = MsgVal("message")
    val messageError = message.join("error")

    case class messageErrorTurnOffSelf() extends MsgVal(messageError.node("turnedOffSelf"))

    case class messageErrorTurnOffOther() extends MsgVal(messageError.node("turnedOffOther"))

    val econ = MsgVal("econ")
    val econError = econ.join("error")

    case class econErrorArgs() extends MsgVal(econError.node("wrongArgs"))

    case class econErrorInvalidCurrency() extends MsgVal(econError.node("invalidCurrency"))

    case class econErrorInvalidAmount() extends MsgVal(econError.node("invalidAmount"))


    val general = MsgVal("general")

    case class generalNoPermission() extends MsgVal(general.node("noPermission"))

    case class generalNotOnline() extends MsgVal(general.node("notOnline"))

    val channel = MsgVal("channel")
    val channelError = channel.join("error")

    case class channelErrorLeaveFocusWhenNoSub() extends MsgVal(channelError.node("cantLeaveFocusWhenNoSub"))

    val channelSub = channel.join("sub")
    val channelLeave = channel.join("leave")
    val channelLeaveError = channelLeave.join("error")
    val channelLeaveList = channelLeave.join("list")

    case class channelLeaveListHeader() extends MsgVal(channelLeaveError.node("header"))

    case class channelLeaveErrorGeneral() extends MsgVal(channelLeaveError.node("cantLeaveGeneralWhenFocused"))

    val channelAlert = channel.join("alert")

    case class channelAlertHeader() extends MsgVal(channelAlert.node("header"))

    case class channelAlertFooter() extends MsgVal(channelAlert.node("footer"))

    val channelAlertError = channelAlert.join("error")

    case class channelAlertErrorArgs() extends MsgVal(channelAlertError.node("wrongArgs"))

}
