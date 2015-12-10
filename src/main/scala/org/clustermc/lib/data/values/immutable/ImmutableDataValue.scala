package org.clustermc.lib.data.values.immutable

import org.clustermc.lib.data.values.DataValueImpl
import org.clustermc.lib.utils.implicits.GenericImplicits

class ImmutableDataValue[T](key: String, private[this] val value: Option[T], override val innerClass: Class[T])
    extends DataValueImpl[T](key, value, innerClass)

object ImmutableDataValue {
    import GenericImplicits.AsOpt

    import scala.reflect.ClassTag

    def apply[T: ClassTag](key: String, value: T, c: Class[T]) = {
        new ImmutableDataValue(key, value.asOpt[T], c)
    }
}
