package org.clustermc.lib.data.values.mutable

object MutableDataValues {

    sealed class IntDataValue(private[this] val _value: Option[Int] = Option(0))
        extends MutableDataValue[Int](_value, classOf[Int])

    object IntDataValue {

        def apply(value: Int = 0): IntDataValue = {
            apply(Option(value))
        }

        def apply(value: Option[Int]) = {
            new IntDataValue(value)
        }
    }
}
