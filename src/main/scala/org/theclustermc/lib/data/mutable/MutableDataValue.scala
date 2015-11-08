package org.theclustermc.lib.data.mutable

import org.theclustermc.lib.data.DataValueImpl
import org.theclustermc.lib.utils.GenericOps.option

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

