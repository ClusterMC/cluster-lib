package org.clustermc.lib.data.mutable

import org.clustermc.lib.data.DefaultDataValue
import org.clustermc.lib.utils.GenericOps

sealed class SettingData[T](private val default: Option[T],
                            private[this] val _value: Option[T] = None,
                            private[this] val _innerClass: Class[T])
    extends MutableDataValue[T](_value, _innerClass) with DefaultDataValue[T] {

    override val _default = default
}

object SettingData {
    import GenericOps.AsOpt

    def apply[T](default: T, value: T = None, c: Class[T]) = {
        new SettingData[T](value.asOpt, value.asOpt, c)
    }
}

sealed class BooleanSetting(private val default: Option[Boolean],
                            private[this] val _value: Option[Boolean] = Option(false))
    extends SettingData[Boolean](default, _value, classOf[Boolean])

object BooleanSetting {

    def apply(default: Boolean, value: Boolean = false): BooleanSetting = {
        apply(Option(default), Option(value))
    }

    def apply(default: Option[Boolean], value: Option[Boolean]) = {
        new BooleanSetting(default, value)
    }
}
