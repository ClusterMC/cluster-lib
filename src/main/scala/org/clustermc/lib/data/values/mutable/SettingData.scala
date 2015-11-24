package org.clustermc.lib.data.values.mutable

import org.clustermc.lib.data.values.DefaultDataValue
import org.clustermc.lib.utils.implicits.GenericImplicits

class SettingData[T](private val default: Option[T],
                            private[this] val _value: Option[T] = None,
                            private[this] val _innerClass: Class[T])
    extends MutableDataValue[T](_value, _innerClass) with DefaultDataValue[T] {

    override val _default = default
}

object SettingData {
    import GenericImplicits.AsOpt

    def apply[T](default: T, value: T = None, c: Class[T]) = {
        new SettingData[T](value.asOpt, value.asOpt, c)
    }
}
