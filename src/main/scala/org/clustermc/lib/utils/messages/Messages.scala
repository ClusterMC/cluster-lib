package org.clustermc.lib.utils.messages

import java.util.regex.Pattern

import net.md_5.bungee.api.ChatColor
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.utils.CustomConfig

import scala.collection.JavaConversions._
import scala.collection.mutable

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object Messages {
  private val cache: mutable.HashMap[String, String] = mutable.HashMap()

  for (key <- config.getKeys(true)) {
    messages.put(key, ChatColor.translateAlternateColorCodes('&', config.getConfig.getString(key)))
  }

  def load(): Unit ={

  }

  private def generate(key: String, values: Option[Array[MsgVar]]): String = {
    val s: StringBuilder = new StringBuilder("Default for " + key)
    if(values.isDefined){
      values.get.foreach(msg => s.append("_").append(msg.identifier))
    }
    cache.put(key, s.toString())

    "GENERATED: " + key
  }

  def apply(key: String): String = {
    if (!cache.containsKey(key)) {
      return generate(key, None)
    }
    cache.get(key).get
  }

  def apply(key: String, values: MsgVar*): String = {
    if (!cache.containsKey(key)) {
      generate(key, Option(values.toArray[MsgVar]))
    } else {
      var s: String = cache.get(key).get
      for(x <- values.toArray[MsgVar]){
        s = s.replace(x.identifier, String.valueOf(x.variable))
      }
      s
    }
  }

}
