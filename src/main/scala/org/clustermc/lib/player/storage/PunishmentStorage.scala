package org.clustermc.lib.player.storage

import org.bson.types.ObjectId
import org.clustermc.lib.punishment.data.Punishment

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class PunishmentStorage {
  var _mute, _ban: Option[ObjectId] = None

  private[player] def muted: Boolean = {
    if(_mute.isDefined){
      val punish = Punishment.timeLeft(_mute.get)
      if(punish.isEmpty || punish.get.getSeconds == 0){
        _mute = None
        return false
      }
      true
    }else false
  }

  private[player] def banned: Boolean = {
    if(_ban.isDefined){
      val punish = Punishment.timeLeft(_ban.get)
      if(punish.isEmpty || punish.get.getSeconds == 0){
        _ban = None
        return false
      }
      true
    }else false
  }

  def loadMuteAndBan(mute: String, ban: String): Unit ={
    if (ban != "none") {
      _ban = Option(new ObjectId(ban))
      banned
    }
    if (mute != "none") {
      _mute = Option(new ObjectId(mute))
      muted
    }
  }

}
