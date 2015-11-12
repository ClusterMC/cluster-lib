package org.clustermc.lib.data.mutable

import org.clustermc.lib.data.DefaultDataValue
import org.clustermc.lib.utils.GenericOps

import scala.reflect.ClassTag

sealed class SettingData[T](private val default: Option[T],
                            override val value: Option[T] = None,
                            override val innerClass: Class[T])
    extends MutableDataValue[T](value, innerClass) with DefaultDataValue[T] {

    override val _default = default
}

object SettingData {
    import GenericOps.option

    def apply[T: ClassTag](default: T, value: T = None)(implicit clazz: Class[T]) = {
        new SettingData(option(default), option(value), clazz)
    }
}

sealed class BooleanSetting(val default: Option[Boolean],
                            override val value: Option[Boolean] = Option(false))
    extends SettingData[Boolean](default, value, classOf[Boolean])

object BooleanSetting {

    def apply(default: Boolean, value: Boolean = false): BooleanSetting = {
        apply(Option(default), Option(value))
    }

    def apply(default: Option[Boolean], value: Option[Boolean]) = {
        new BooleanSetting(default, value)
    }
}
