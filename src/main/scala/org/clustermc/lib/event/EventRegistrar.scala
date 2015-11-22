package org.clustermc.lib.event

import org.bukkit.event.Event

import scala.collection.immutable.HashMap

trait EventRegistrar {

    private var _consumers: Map[Class[_ <: Event], List[EventConsumer[_ <: Event]]] = HashMap()

    def consumers = _consumers

    def clean() = _consumers = HashMap()

    def dispatch[E <: Event](event: E): E = {
        val eClass = event.getClass
        _consumers.get(eClass) match {
            case Some(list) =>
                list.foreach { case c: EventConsumer[_] if c.eventClass.equals(eClass) => println("Type check works") }
        }
        event
    }

    def register[E <: Event](consumer: EventConsumer[E]): Unit = {
        val eClass = consumer.eventClass
        _consumers.get(eClass) match {
            case Some(list) => _consumers += (eClass -> (consumer :: list))
            case None => _consumers += (eClass -> List(consumer))
        }
    }

    def unregister[E <: Event](consumer: EventConsumer[E]): Unit = {
        val eClass = consumer.eventClass
        _consumers.get(eClass) match {
            case Some(list) => list filterNot { _ equals consumer }
        }
    }

    def unregister[E <: Event](eventClass: Class[E]): Unit = {
        _consumers -= eventClass
    }
}
