package org.clustermc.lib.data.values.mutable.impl

import org.clustermc.lib.data.values.mutable.SettingData

object SettingDataValues {

    sealed class BooleanSetting(key: String, private val default: Option[Boolean],
                                private[this] val value: Option[Boolean] = Option(false))
        extends SettingData[Boolean](key, default, value, classOf[Boolean]) {

        def has = value match {
            case Some(b) => b
            case _ => false
        }

        override def isDefault: Boolean = value == _default

        override def deserialize(s: String) = _value = Option(s.toBoolean)
    }

    object BooleanSetting {

        def apply(key: String, default: Boolean, value: Boolean = false): BooleanSetting = {
            apply(key, Option(default), Option(value))
        }

        def apply(key:String, default: Option[Boolean], value: Option[Boolean]) = {
            new BooleanSetting(key, default, value)
        }
    }
}
