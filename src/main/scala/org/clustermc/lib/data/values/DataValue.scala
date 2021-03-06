package org.clustermc.lib.data.values

import org.bson.Document

trait DataValue[T] {
    private[data] var _key = this.getClass.getSimpleName.toLowerCase

    def key = _key

    def key_=(s: String) = _key = s

    protected var _value: Option[T] = None

    def value = _value

    def valueOr(t: T): T = {
        _value match {
            case Some(v) => v
            case None => t
        }
    }

    def load(o: Any)

    def deserialize(s: String): Unit = _value = None

    def serialize = if(_value.isDefined) Some(_value.get.toString) else None

    def appendTo(document: Document) = {
        val serialized = serialize
        if(serialized.isDefined) {
            document.append(key, serialized.get)
        }
    }

    def print() = {
        val doc: Document = new Document()
        this.appendTo(doc)
        println(doc.toJson)
    }
}

class DataValueImpl[T](key: String, private[this] val value: Option[T], val innerClass: Class[T])
    extends DataValue[T] {
    _key = key

    override def load(o: Any) = {
        o.getClass match {
            case `innerClass` => _value = Option(innerClass.cast(o))
            case _ => _value = None
        }
    }
}

class ClassDataValue[T](key: String, private[this] val value: Option[Class[T]])
    extends DataValue[Class[T]] {
    _key = key

    override def load(o: Any): Unit = {
        o match {
            case c: Class[T] => _value = Option(c)
            case _ => _value = None
        }
    }
}
