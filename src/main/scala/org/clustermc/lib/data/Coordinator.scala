package org.clustermc.lib.data

import scala.collection.mutable

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */
trait Coordinator[K, V, L] {

  protected val coordinatorMap: mutable.HashMap[K, V] = mutable.HashMap()

  def apply(key: K): V = coordinatorMap.get(key).get

  def has(key: K): Boolean = coordinatorMap.contains(key)
  def set(key: K, value: V) = coordinatorMap.put(key, value)
  def remove(key: K) = if(has(key)) coordinatorMap.remove(key)

  def load(load: L)
  def unloadAll(): Unit
  def unload(key: K): Unit
}

trait KeyLoadingCoordinator[K, V] extends Coordinator[K, V, K] {
  override def apply(key: K): V = {
    if (!has(key)) load(key)
    coordinatorMap.get(key).get
  }
}
