package org.clustermc.lib.data.values.mutable.impl

import org.clustermc.lib.data.values.mutable.MutableDataValue

object MutableDataValues {

    sealed class IntDataValue(key:String, private[this] val _value: Option[Int] = Option(0))
        extends MutableDataValue[Int](key, _value, classOf[Int]) {

        override def deserialize(s: String) = value = s.toInt
    }

    object IntDataValue {

        def apply(key: String, value: Int = 0): IntDataValue = {
            apply(key, Option(value))
        }

        def apply(key: String, value: Option[Int]) = {
            new IntDataValue(key, value)
        }
    }
}
