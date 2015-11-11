package org.clustermc.lib.data.mutable

import org.clustermc.lib.data.DataValue

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

trait MutableListDataValue[T] extends DataValue[mutable.ArrayBuffer[Option[T]]]

class MutableListDataValueImpl[T](private[this] val value: Option[mutable.ArrayBuffer[Option[T]]],
                                  val innerClass: Class[T])
    extends MutableListDataValue[T] {
    _value = value

    override def load(o: Any) = {
        val list: ArrayBuffer[Option[T]] = ArrayBuffer()
        o match {
            case l: java.util.List[_] => l.asScala.foreach(v => {
                v.getClass match {
                    case `innerClass` => list.append(Option(innerClass.cast(o)))
                }
            })
        }
        _value = Option(list)
    }
}
