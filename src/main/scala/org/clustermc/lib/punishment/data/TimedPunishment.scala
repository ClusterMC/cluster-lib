package org.clustermc.lib.punishment.data

import java.util.UUID

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class TimedPunishment(punisher: UUID, punished: UUID, reason: String, ) extends PunishmentData(punisher, punished, reason){

}
