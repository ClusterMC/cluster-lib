package org.theclustermc.lib.data

import org.bson.Document

trait DefaultDataValue[T] extends DataValue[T] {
    private[data] val _default: Option[T]

    override def toDocument: Document = {
        super.toDocument.append("isDefault", isDefault)
    }

    def isDefault = value.equals(_default)
}
