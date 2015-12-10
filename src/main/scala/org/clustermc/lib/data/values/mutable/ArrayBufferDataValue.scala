package org.clustermc.lib.data.values.mutable

import org.clustermc.lib.data.values.WrappedDataValueImpl

import scala.collection.mutable.ArrayBuffer
import scala.language.reflectiveCalls

class ArrayBufferDataValue[T](key: String, private[this] val _wrapped: Option[ArrayBuffer[T]], val tClass: Class[T])
    extends WrappedDataValueImpl[T, ArrayBuffer](key, _wrapped, tClass, classOf[ArrayBuffer[T]])

object ArrayBufferDataValue {
    def apply[T](key: String, value: Option[ArrayBuffer[T]], c: Class[T]) = {
        new ArrayBufferDataValue[T](key, value, c)
    }
}
