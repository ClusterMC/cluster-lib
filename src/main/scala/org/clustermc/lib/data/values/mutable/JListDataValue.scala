package org.clustermc.lib.data.values.mutable

import java.util

import org.clustermc.lib.data.values.WrappedDataValueImpl

import scala.language.reflectiveCalls

class JListDataValue[T](key: String, private[this] val _wrapped: Option[util.List[T]], val tClass: Class[T])
    extends WrappedDataValueImpl[T, util.List](key, _wrapped, tClass, classOf[util.List[T]])

object JListDataValue {
    def apply[T](key: String, value: Option[util.List[T]], c: Class[T]) = {
        new JListDataValue[T](key, value, c)
    }
}
