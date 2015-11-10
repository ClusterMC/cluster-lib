package org.theclustermc.lib.data

import org.bson.Document
import org.theclustermc.lib.utils.ClosureImplicits._

trait ListDataValue[T] extends DataValue[List[Option[T]]]

class ListDataValueImpl[T](private[this] val value: Option[List[Option[T]]], val innerClass: Class[T])
    extends ListDataValue[T] {
    _value = value

    override def load(doc: Document) = {
        val o = doc.get(name)
        var list: List[Option[T]] = List()
        o match {
            case l: java.util.List => l.forEach(consumer(v => {
                v.getClass match {
                    case `innerClass` => list = Option(innerClass.cast(o)) :: list
                }
            }))
        }
        _value = Option(list)
    }
}
