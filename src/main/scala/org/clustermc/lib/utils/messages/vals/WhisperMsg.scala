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

object WhisperMsg {
  val whisper = MsgVal("whisper")

  val format = whisper.join("format")
  case class whisperFormatSender(to: String, message: String) extends MsgVal(format.node("sender"),
    MsgVar("to", to), MsgVar("message", message))
  case class whisperFormatReceiver(from: String, message: String) extends MsgVal(format.node("receiver"),
    MsgVar("from", from), MsgVar("message", message))

  val error = whisper.join("error")
  case class whisperErrorTurnOffSelf() extends MsgVal(error.node("turnedOffSelf"))
  case class whisperErrorTurnOffOther() extends MsgVal(error.node("turnedOffOther"))
}
