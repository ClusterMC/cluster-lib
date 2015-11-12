package org.clustermc.lib.data

import scala.collection.JavaConverters._

trait ListDataValue[T] extends DataValue[List[Option[T]]]

class ListDataValueImpl[T](private[this] val value: Option[List[Option[T]]],
                           val innerClass: Class[T])
    extends ListDataValue[T] {
    _value = value

    override def load(o: Any) = {
        var list: List[Option[T]] = List()
        o match {
            case l: java.util.List[_] => l.asScala.foreach(v => {
                v.getClass match {
                    case `innerClass` => list = Option(innerClass.cast(o)) :: list
                }
            })
        }
        _value = Option(list)
    }
}
