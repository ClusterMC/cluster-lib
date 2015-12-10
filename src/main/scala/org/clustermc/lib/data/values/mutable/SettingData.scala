package org.clustermc.lib.data.values.mutable

import org.clustermc.lib.data.values.DefaultDataValue
import org.clustermc.lib.utils.implicits.GenericImplicits

class SettingData[T](key: String, private val default: Option[T],
                            private[this] val _value: Option[T] = None,
                            private[this] val _innerClass: Class[T])
    extends MutableDataValue[T](key, _value, _innerClass) with DefaultDataValue[T] {

    override val _default = default
}

object SettingData {
    import GenericImplicits.AsOpt

    def apply[T](key: String, default: T, value: T = None, c: Class[T]) = {
        new SettingData[T](key, value.asOpt, value.asOpt, c)
    }
}
