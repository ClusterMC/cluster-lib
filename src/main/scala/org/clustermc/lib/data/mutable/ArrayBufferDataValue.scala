package org.clustermc.lib.data.mutable

import org.clustermc.lib.data.WrappedDataValueImpl

import scala.collection.mutable.ArrayBuffer
import scala.language.reflectiveCalls

class ArrayBufferDataValue[T](private[this] val _wrapped: Option[ArrayBuffer[T]])
                      (implicit tClass: Class[T])
    extends WrappedDataValueImpl[T, ({type V[B] = ArrayBuffer[B]})#V](_wrapped, tClass, classOf[ArrayBuffer[T]])
