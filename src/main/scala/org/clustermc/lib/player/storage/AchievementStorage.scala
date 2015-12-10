package org.clustermc.lib.player.storage

import org.clustermc.lib.achievements.LocationAchievements

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

class AchievementStorage {

  var achievements: Set[LocationAchievements] = Set()

  def has(achievement: LocationAchievements): Boolean = {
    achievements.contains(achievement)
  }

  def unlock(achievement: LocationAchievements): Boolean ={
    if(!has(achievement)){
      achievements += achievement
      return true
    }
    false
  }

  def deserialize(input: String): Unit ={
    if(input != "none"){
      input.split(";").foreach(s => unlock(LocationAchievements.valueOf(s)))
    }
  }

  def serialize(): String ={
    var string: String = ""
    achievements.foreach(x => string += (x.name() + ";"))
    if(string.length > 1){
      string = new StringBuffer(string).deleteCharAt(string.length - 1).toString
    }else{
      string = "none"
    }
    string
  }

}
