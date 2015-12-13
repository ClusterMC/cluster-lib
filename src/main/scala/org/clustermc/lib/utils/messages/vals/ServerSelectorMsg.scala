package org.clustermc.lib.utils.messages.vals

import org.clustermc.lib.utils.messages.MsgVal

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object ServerSelectorMsg {
  val serverSelector = MsgVal("serverSelector")

  val error = serverSelector.join("error")
  class serverSelectorWaitToUse() extends MsgVal(error.node("waitToUse"))

}
