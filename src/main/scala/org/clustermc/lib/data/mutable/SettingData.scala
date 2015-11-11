package org.clustermc.lib.data.mutable

import org.clustermc.lib.data.DefaultDataValue
import org.clustermc.lib.utils.GenericOps

import scala.reflect.ClassTag

sealed class SettingData[T](private val d: Option[T], private val v: Option[T] = None,
                            override val innerClass: Class[T])
    extends MutableDataValue[T](v, innerClass) with DefaultDataValue[T] {

    override val _default = d
}

object SettingData {
    import GenericOps.option

    def apply[T: ClassTag](default: T, value: T = None)(implicit clazz: Class[T]) = {
        new SettingData(option(default), option(value), clazz)
    }
}

sealed class BooleanSetting(private val v: Option[Boolean] = None)
    extends SettingData[Boolean](Option(false), v, classOf[Boolean])

object BooleanSetting {
    import GenericOps.option

    def apply[T: ClassTag](default: T, value: T = None) = {
        new SettingData(option(default), option(value), classOf[Boolean])
    }
}
