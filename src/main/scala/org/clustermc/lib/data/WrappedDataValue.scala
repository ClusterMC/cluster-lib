package org.clustermc.lib.data

import scala.language.higherKinds

trait WrappedDataValue[U, W[V >: U]] extends DataValue[U] {
    val wrappedClass: Class[W[U]]
}

class WrappedDataValueImpl[U, W[V >: U]](private[this] val value: Option[W[U]],
                                    val innerClass: Class[U],
                                    val wrappedClass: Class[W[U]])
    extends WrappedDataValue[U, W] {

    override def load(o: Any) = {
        //still gotta do
    }
}
