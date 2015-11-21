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
  private val messages: mutable.HashMap[String, String] = mutable.HashMap()
  private val config = new CustomConfig(ClusterLib.instance.getDataFolder, "lang")

  for (key <- config.getKeys(true)) {
    messages.put(key, ChatColor.translateAlternateColorCodes('&', config.getConfig.getString(key)))
  }

  private def generate(key: String, values: Option[Array[MsgVar]]): String = {
    val s: StringBuilder = new StringBuilder("Default_for_" + key.replaceAll(Pattern.quote("."), "_"))
    if(values.isDefined){
      values.get.foreach(msg => s.append("_").append(msg.identifier))
    }
    config.set(key, s.toString(), true)
    messages.put(key, s.toString())
    s.toString()
  }

  def apply(key: String): String = {
    if (!messages.containsKey(key)) {
      generate(key, None)
      return "Generated default for " + key
    }
    messages.get(key).get
  }

  def apply(key: String, values: MsgVar*): String = {
    if (!messages.containsKey(key)) {
      generate(key, Option(values.toArray[MsgVar]))
      "Generated default for " + key
    } else {
      val s: String = messages.get(key).get
      values.toArray[MsgVar].foreach(vari => s.replace(vari.identifier, String.valueOf(vari.variable)))
      s
    }
  }

}
