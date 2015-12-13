package org.clustermc.lib.utils.messages

import org.bson.Document
import org.clustermc.lib.ClusterLib

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

  def load(): Unit ={
    ClusterLib.instance.database.getDatabase.getCollection("lang").find().foreach(doc=>
      cache.put(doc.getString("key"), doc.getString("message")))
  }

  private def generate(key: String, values: Option[Array[MsgVar]]): String = {
    val s: StringBuilder = new StringBuilder("Default for " + key)
    if(values.isDefined){
      values.get.foreach(msg => s.append("_").append(msg.identifier))
    }
    ClusterLib.instance.database.getDatabase.getCollection("lang")
      .insertOne(new Document().append("key", key).append("message", s.toString()))
    cache.put(key, s.toString())
    "GENERATED: " + key
  }

  def apply(key: String): String = {
    if (!cache.containsKey(key)) {
      return generate(key, None)
    }
    cache.get(key).get
  }

  def apply(key: String, values: List[MsgVar]): String = {
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
