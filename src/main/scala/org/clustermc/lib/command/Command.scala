package org.clustermc.lib.command

import org.clustermc.lib.command.args.Arg
import org.clustermc.lib.enums.PermissionRank

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class Command(args: Arg*) {

  val permission: PermissionRank
  val needsPlayer: Boolean

}
