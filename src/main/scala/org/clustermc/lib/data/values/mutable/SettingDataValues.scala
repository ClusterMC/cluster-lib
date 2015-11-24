package org.clustermc.lib.data.values.mutable

object SettingDataValues {

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
}
