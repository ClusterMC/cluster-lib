package org.theclustermc.lib.data.immutable

import org.theclustermc.lib.data.DataValueImpl

class ImmutableDataValue[T](private[this] val value: Option[T], override val innerClass: Class[T])
    extends DataValueImpl[T](value, innerClass)

object ImmutableDataValue {
    import org.theclustermc.lib.utils.GenericOps.option

    import scala.reflect.ClassTag

    def apply[T: ClassTag](value: T)(implicit clazz: Class[T]) = {
        new ImmutableDataValue(option(value), clazz)
    }
}
