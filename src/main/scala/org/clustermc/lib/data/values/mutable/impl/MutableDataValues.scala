package org.clustermc.lib.data.values.mutable.impl

import org.clustermc.lib.data.values.mutable.MutableDataValue

object MutableDataValues {

    sealed class IntDataValue(private[this] val _value: Option[Int] = Option(0))
        extends MutableDataValue[Int](_value, classOf[Int]) {

        override def deserialize(s: String) = value = s.toInt
    }

    object IntDataValue {

        def apply(value: Int = 0): IntDataValue = {
            apply(Option(value))
        }

        def apply(value: Option[Int]) = {
            new IntDataValue(value)
        }
    }
}
