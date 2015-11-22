package org.clustermc.lib.punishment.data

import java.time.{Duration, Instant}
import java.util.UUID

import com.mongodb.client.model.Filters
import org.bson.Document
import org.bson.types.ObjectId
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.punishment.PunishmentType

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
//For new punishments
class PunishmentAct(val ptype: PunishmentType, val punisher: UUID, val punished: UUID,
                        val reason: String, val duration: Duration){
  val objectId = new ObjectId()
  val start = Instant.now()
  val end = start.plus(duration)
  lazy val timed: Boolean = end.isAfter(start)

  def save(): Unit ={
    ClusterLib.instance.database.getDatabase("punishment").getCollection("log").insertOne(toDocument)
  }

  def toDocument: Document = {
    new Document()
      .append("_id", objectId)
      .append("people", new Document()
        .append("punished", punished.toString)
        .append("punisher", punisher.toString))
      .append("time", new Document()
        .append("start", start.toString)
        .append("end", end.toString))
      .append("type", ptype.toString)
      .append("reason", reason)
  }
}

//For a punishment from the database
class PunishmentData(val ptype: PunishmentType, val punisher: UUID, val punished: UUID, val reason: String,
                 val start: Instant, val end: Instant, val objectId: ObjectId){
  lazy val timed: Boolean = end.isAfter(start)
  def finished: Boolean = if(timed) Instant.now().isAfter(end) else false
}

object Punishment{
  def create(ptype: PunishmentType, punisher: UUID,  punished: UUID,  reason: String, duration: Duration): PunishmentAct ={
    new PunishmentAct(ptype, punisher, punished, reason, duration)
    ptype match {
      //TODO
    }
  }

  def create(ptype: PunishmentType, punisher: UUID,  punished: UUID,  reason: String): PunishmentAct ={
    create(ptype, punisher, punished, reason, Duration.ZERO)
  }

  def load(objectId: ObjectId): PunishmentData ={
    val doc: Document = ClusterLib.instance.database.getDatabase("punishment")
      .getCollection("log").find(Filters.eq("_id", objectId)).first()
    new PunishmentData(PunishmentType.valueOf(doc.getString("type")),
      UUID.fromString(doc.getString("people.punisher")),
      UUID.fromString(doc.getString("people.punished")),
      doc.getString("reason"),
      Instant.parse(doc.getString("time.start")),
      Instant.parse(doc.getString("time.end")),
      doc.getObjectId("_id"))
  }

  def reason(objectId: ObjectId): String = {
    ClusterLib.instance.database.getDatabase("punishment")
      .getCollection("log").find(Filters.eq("_id", objectId)).first()
      .getString("reason")
  }

}
