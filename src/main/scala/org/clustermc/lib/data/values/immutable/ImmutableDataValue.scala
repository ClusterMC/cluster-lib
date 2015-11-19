package org.clustermc.lib.data.values.immutable

import org.clustermc.lib.data.values.DataValueImpl
import org.clustermc.lib.utils.implicits.GenericImplicits

class ImmutableDataValue[T](private[this] val value: Option[T], override val innerClass: Class[T])
    extends DataValueImpl[T](value, innerClass)

object ImmutableDataValue {
    import GenericImplicits.AsOpt

    import scala.reflect.ClassTag

    def apply[T: ClassTag](value: T, c: Class[T]) = {
        new ImmutableDataValue(value.asOpt[T], c)
    }
}
