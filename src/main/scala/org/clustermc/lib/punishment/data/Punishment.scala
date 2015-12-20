package org.clustermc.lib.punishment.data

import java.time.{Duration, Instant}
import java.util.UUID

import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.types.ObjectId
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.punishment.PunishmentType
import org.json.JSONObject

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Default (Template) Project.
 * 
 * Default (Template) Project can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

object Punishment{
  def create(ptype: PunishmentType, punisher: UUID,  punished: UUID,  reason: String, duration: Duration): PunishmentAct ={
    val punishment = new PunishmentAct(ptype, punisher, punished, reason, duration)
    punishment.save()
    punishment
  }

  def create(ptype: PunishmentType, punisher: UUID,  punished: UUID,  reason: String): PunishmentAct ={
    create(ptype, punisher, punished, reason, Duration.ZERO)
  }

  def load(objectId: ObjectId): PunishmentData ={
    val doc: Document = ClusterLib.instance.database.getDatabase
      .getCollection("punishmentlog").find(Filters.eq("_id", objectId)).first()
    val obj = new JSONObject(doc.toJson)
    println(doc.getObjectId("_id"))
    new PunishmentData(PunishmentType.valueOf(obj.getString("type")),
      UUID.fromString(obj.getJSONObject("people").getString("punisher")),
      UUID.fromString(obj.getJSONObject("people").getString("punished")),
      obj.getString("reason"),
      Instant.parse(obj.getJSONObject("time").getString("start")),
      Instant.parse(obj.getJSONObject("time").getString("end")),
      doc.getObjectId("_id"))
  }

  def timeLeft(objectId: ObjectId): Option[Duration] = {
    val doc = ClusterLib.instance.database.getDatabase
      .getCollection("punishmentlog").find(Filters.eq("_id", objectId)).first()
    val obj = new JSONObject(doc.toJson)
    println(doc.toJson)
    val end = obj.getJSONObject("time").getString("end")
    val start = obj.getJSONObject("time").getString("start")
    if(end == start){
      Option(Duration.ZERO)
    }else{
      val endI = Instant.parse(end)
      if(Instant.now().isAfter(endI)){
        println("now is after end")
        None
      }else{
        Option(Duration.between(Instant.now(), endI))
      }
    }
  }

  def reason(objectId: ObjectId): String = {
    ClusterLib.instance.database.getDatabase
      .getCollection("punishmentlog").find(Filters.eq("_id", objectId)).first()
      .getString("reason")
  }

}
