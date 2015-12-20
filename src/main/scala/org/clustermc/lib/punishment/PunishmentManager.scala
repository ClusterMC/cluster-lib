package org.clustermc.lib.punishment

import java.time.Duration
import java.util.UUID

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object PunishmentManager {

  //todo
  def ban(uuid: UUID, punisher: UUID, punished: UUID, reason: String, executor: Boolean = true): Unit ={
    if(executor){
      //LibPlayer.
    }
    /*ppunished.punishments._ban = Option(
      Punishment.create(PunishmentType.BAN, punisher, punished, reason)
        .objectId)*/
  }

  def tempban(uuid: UUID, time: Duration, punisher: UUID, punished: UUID, reason: String): Unit ={

  }

  def unban(uuid: UUID, punisher: UUID, punished: UUID, reason: String): Unit ={

  }


  def mute(uuid: UUID, punisher: UUID, punished: UUID, reason: String): Unit ={

  }

  def tempmute(uuid: UUID, time: Duration, punisher: UUID, punished: UUID, reason: String): Unit ={

  }

  def unmute(uuid: UUID, punisher: UUID, punished: UUID, reason: String): Unit ={

  }

}
