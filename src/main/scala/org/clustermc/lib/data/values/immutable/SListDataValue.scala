package org.clustermc.lib.data.values.immutable

import org.clustermc.lib.data.values.WrappedDataValueImpl

import scala.language.reflectiveCalls

class SListDataValue[T](key: String, private[this] val _wrapped: Option[List[T]], val tClass: Class[T])
    extends WrappedDataValueImpl[T, List](key, _wrapped, tClass, classOf[List[T]]) {

    def ::=(prepend: T) = {
        checkExistence
        wrapped = prepend :: extract
    }

    def :::=(prepend: List[T]) = {
        checkExistence
        wrapped = prepend ::: extract
    }

    def diff(difference: T) = {
        diff(List(difference))
    }

    def diff(difference: List[T]) = {
        checkExistence
        wrapped = extract diff difference
    }
}

object SListDataValue {
    def apply[T](key: String, value: Option[List[T]], c: Class[T]) = {
        new SListDataValue[T](key, value, c)
    }
}
