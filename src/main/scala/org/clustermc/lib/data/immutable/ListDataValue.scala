package org.clustermc.lib.data.immutable

import org.clustermc.lib.data.WrappedDataValueImpl

import scala.language.reflectiveCalls

class ListDataValue[T](private[this] val _wrapped: Option[List[T]])
                      (implicit tClass: Class[T])
    extends WrappedDataValueImpl[T, ({type V[B] = List[B]})#V](_wrapped, tClass, classOf[List[T]])
