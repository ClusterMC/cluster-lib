package org.clustermc.lib.data.mutable

import org.clustermc.lib.data.DataValueImpl
import org.clustermc.lib.utils.GenericOps.AsOpt

class MutableDataValue[T](private[this] override var value: Option[T], override val innerClass: Class[T])
    extends DataValueImpl[T](value, innerClass) {

    def value_=(value: T) = _value = value.asOpt

    def value_=(value: Option[T]) = _value = value
}

object MutableDataValue {

    def apply[T](value: T, c: Class[T]) = {
        new MutableDataValue(value.asOpt, c)
    }
}

