package org.clustermc.lib.event

import org.bukkit.event.{Event, EventPriority}

case class EventConsumer[E <: Event](val eventClass: Class[E], val handler: (E) => Unit) {

    private var _priority: EventPriority = EventPriority.NORMAL

    def priority = _priority

    def priority_(nPriority: EventPriority) = _priority = nPriority

    def consume(event: E): Unit = handler.apply(event)
}
