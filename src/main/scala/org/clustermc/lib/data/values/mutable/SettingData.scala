package org.clustermc.lib.data.values.mutable

import org.clustermc.lib.data.values.DefaultDataValue
import org.clustermc.lib.utils.implicits.GenericImplicits

sealed class SettingData[T](private val default: Option[T],
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

sealed class BooleanSetting(private val default: Option[Boolean],
                            private[this] val _value: Option[Boolean] = Option(false))
    extends SettingData[Boolean](default, _value, classOf[Boolean]) {

    def has = _value match {
        case Some(b) => b
        case _ => false
    }

    override def isDefault: Boolean = _value == _default
}

object BooleanSetting {

    def apply(default: Boolean, value: Boolean = false): BooleanSetting = {
        apply(Option(default), Option(value))
    }

    def apply(default: Option[Boolean], value: Option[Boolean]) = {
        new BooleanSetting(default, value)
    }
}
