package org.clustermc.lib.data.mutable

import org.clustermc.lib.data.DataValueImpl
import org.clustermc.lib.utils.GenericOps
import GenericOps.option

import scala.reflect.ClassTag

class MutableDataValue[T](private[this] override var value: Option[T], override val innerClass: Class[T])
    extends DataValueImpl[T](value, innerClass) {

    def value_=(value: T)(implicit tTag: ClassTag[T]) = _value = option(value)
}

object MutableDataValue {

    def apply[T: ClassTag](value: T)(implicit clazz: Class[T]) = {
        new MutableDataValue(option(value), clazz)
    }
}

