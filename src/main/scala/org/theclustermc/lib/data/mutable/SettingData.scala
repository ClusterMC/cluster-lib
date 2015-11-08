package org.theclustermc.lib.data.mutable

import org.theclustermc.lib.data.DefaultDataValue
import scala.reflect.ClassTag

sealed class SettingData[T](private val d: Option[T], private val v: Option[T] = None)
    extends MutableDataValueImpl[T](v) with DefaultDataValue[T] {

    override val _default = d
}

object SettingData {
    import org.theclustermc.lib.utils.GenericOps.option

    def apply[T: ClassTag](default: T, value: T = None) = {
        new SettingData(option(default), option(value))
    }
}
