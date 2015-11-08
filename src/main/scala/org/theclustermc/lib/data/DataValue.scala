package org.theclustermc.lib.data

import org.bson.Document
import org.theclustermc.lib.utils.ClosureImplicits.consumer
import org.theclustermc.lib.utils.database.MongoObject

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
