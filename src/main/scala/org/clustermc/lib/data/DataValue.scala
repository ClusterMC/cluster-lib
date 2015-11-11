package org.clustermc.lib.data

import org.bson.Document
import org.clustermc.lib.utils.ClosureImplicits
import org.clustermc.lib.utils.database.MongoObject
import ClosureImplicits.consumer

trait DataValue[T] extends MongoObject {
    val name = this.getClass.getSimpleName.toLowerCase

    private[data] var _value: Option[T] = None

    def value = _value

    def deserialize(t: String) = this

    override def getIndex: String = ???

    override def getCollection: String = ???

    override def getID: String = name

    override def toDocument: Document = {
        val document = new Document()
        val serialized = serialize

        if(serialized.isDefined) {
            document.append(name, serialized.get)
        }
        document
    }

    def serialize = if(_value.isDefined) Some(_value.toString) else None

    override def load(doc: Document) = {}

    def appendTo(document: Document) = {
        val thisDoc = toDocument
        thisDoc.entrySet().forEach(consumer(e => document.append(e.getKey, e.getValue)))
    }

    def print() = {
        println(toDocument.toJson)
    }
}

class DataValueImpl[T](private[this] val value: Option[T], val innerClass: Class[T])
    extends DataValue[T] {

    override def load(doc: Document) = {
        val o = doc.get(name)
        o.getClass match {
            case `innerClass` => _value = Option(innerClass.cast(o))
            case _ => _value = None
        }
    }
}
