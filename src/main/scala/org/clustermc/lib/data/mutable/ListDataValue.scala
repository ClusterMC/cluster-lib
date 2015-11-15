package org.clustermc.lib.data.mutable

import org.clustermc.lib.data.WrappedDataValueImpl
import java.util

import scala.language.reflectiveCalls

class ListDataValue[T](private[this] val _wrapped: Option[util.List[T]])
                      (implicit tClass: Class[T])
    extends WrappedDataValueImpl[T, ({type V[B] = util.List[B]})#V](_wrapped, tClass, classOf[util.List[T]])

