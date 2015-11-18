package org.clustermc.lib.utils.event

import org.bukkit.event.Event

class EventConsumer[E <: Event](val eventClass: Class[E], val handler: (E) => Unit)

