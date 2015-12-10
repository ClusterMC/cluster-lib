package org.clustermc.lib.data.values

import org.bson.Document

/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Default (Template) Project.
 * 
 * Default (Template) Project can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

trait DefaultDataValue[T] extends DataValue[T] {
    private[data] val _default: Option[T]

    override def appendTo(document: Document) = {
        super.appendTo(document)
        document.append("isDefault", isDefault)
    }

    def isDefault = value.equals(_default)
}
