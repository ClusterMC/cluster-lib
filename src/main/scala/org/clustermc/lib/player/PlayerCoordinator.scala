package org.clustermc.lib.player

import java.util.UUID

import com.mongodb.client.MongoCollection
import org.bson.Document
import org.clustermc.lib.ClusterLib
import org.clustermc.lib.data.KeyLoadingCoordinator
import org.clustermc.lib.player.ActionResult.ActionResult

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Default (Template) Project.
 * 
 * Default (Template) Project can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

abstract class PlayerCoordinator[T <: PlayerWrapper]() extends KeyLoadingCoordinator[UUID, T]{
  val index = "uuid"

  /**
    * Applies the unload method to all that are currently loaded.
    * Uses an iterator to avoid concurrent modification exceptions
    */
  override def unloadAll(): Unit = coordinatorMap.keysIterator.foreach(unload)

  /**
    * This method unloads the player from the cache (if they are loaded)
    * and then saves their data back to the database.
    *
    * @param key the uuid of the user we are trying to unload
    */
  override def unload(key: UUID): Unit = {
    if (loaded(key)) {
      beforeUnload(key)
      apply(key).save(collection())
      remove(key)
    }
  }

  /**
    * This method lets us provide safe checks for the "action(s)" to be applied to the player, each action
    * has a check done before it to make sure the player data doesnt get messed up by cross-server or nonexistence
    *
    * @param uuid The uuid of the player we are attempting to apply the action to
    * @param allowOtherServer Whether we should allow an action to be applied to them on other servers
    * @param action The function of actions that shall be applied to the player, regardless of
    *               what server they are on
    * @param allowOffline Whether we should allow this action to apply to an
    *                     offline player, meaning no on any of the network branches.
    * @tparam U The return type of the "action" function, never used.
    * @return The ActionResult of the action we are trying to perform, see ActionResult.scala
    */
  def act[U](uuid: UUID, action: T => U, allowOffline: Boolean = false, allowOtherServer: Boolean = false): ActionResult ={
    if(loaded(uuid)){
      action.apply(apply(uuid))
      ActionResult.ONLINE_CURRENT_APPLIED
    }else if(allowOtherServer && isOnline(uuid).isDefined){

      ActionResult.ONLINE_OTHER_APPLIED
    }else if(allowOffline){
      val doc = exists(uuid)
      if(doc.isDefined){
        action.apply(apply(uuid))
        unload(uuid)
        return ActionResult.OFFLINE_APPLIED
      }
      ActionResult.NO_EXIST
    }else{
      ActionResult.NOT_APPLIED
    }
  }

  /**
    * Load (cache) a player into memory for quicker access in the future,
    * Used in apply, and PlayerIO only.
    *
    * @param uuid the uuid identification of the player we are trying to cache
    */
  override def load(uuid: UUID): Unit = {
    if (!loaded(uuid)) {
      val player: T = genericInstance(uuid)
      val doc = exists(uuid)
      if (doc.isDefined){
        player.load(doc.get)
      }
      set(uuid, player)
      afterLoad(uuid)
      //TODO see if this VVV effected anything
      //player.save(collection())
    }
  }

  /**
    * Checks if the player is online on this server, or if they are
    * online on any other of the servers
    *
    * @param uuid the uuid of the player we are checking for
    * @return None if player is not online, UUID/String tuple if online
    */
  def isOnline(uuid: UUID): Option[(UUID, String)] ={
    val doc = ClusterLib.instance.database.getDatabase.getCollection("online")
      .find(new Document(index, uuid.toString)).first()
    if(doc == null){
      None
    }else{
      Option(UUID.fromString(doc.getString("uuid")), doc.getString("server"))
    }
  }

  def exists(uuid: UUID): Option[Document] ={
    val doc = collection().find(new Document(index, uuid.toString)).first()
    if(doc != null){
      Option(doc)
    }else{
      None
    }
  }

  def collection(): MongoCollection[Document] = {
    ClusterLib.instance.database.getDatabase.getCollection(this.getClass.getSimpleName)
  }

  protected def genericInstance(uuid: UUID): T

  protected def beforeUnload(uuid: UUID)

  protected def afterLoad(uuid: UUID)

}

object ActionResult extends Enumeration {
  type ActionResult = Value
  val NO_EXIST = Value("NO_EXIST")
  val ONLINE_OTHER_APPLIED = Value("ONLINE_OTHER_APPLIED")
  val ONLINE_CURRENT_APPLIED = Value("ONLINE_CURRENT_APPLIED")
  val OFFLINE_APPLIED = Value("OFFLINE_APPLIED")
  val NOT_APPLIED = Value("NOT_APPLIED")
}
