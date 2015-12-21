package org.clustermc.lib.xserver.messages

import org.clustermc.lib.xserver.XMessageIdentifier.XMessageIdentifier

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

case class XPlayerMessage(player: String, identifier: XMessageIdentifier, data: String) extends XMessage{
  override val action: String = "ForwardToPlayer"

  override def send(): Unit = {
    send(player, identifier, data)
  }
}
