package org.clustermc.lib.xserver.messages

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object XServerMessage extends XMessage{
  override val action: String = _

  def send(lineTwo: String, data: String, online: Boolean = true): Unit ={
    send(lineTwo, if(online) "ONLINE" else "ALL", data)
  }

}
