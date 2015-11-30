package org.clustermc.lib.data.values

import scala.language.higherKinds

trait WrappedDataValue[U, W[V <: U]] extends DataValue[U]

class WrappedDataValueImpl[U, W[V <: U]](key: String, private[this] var _wrapped: Option[W[U]],
                                         val innerClass: Class[U],
                                         val wrappedClass: Class[W[U]])
    extends WrappedDataValue[U, W] {
    _name = key

    def wrapped = _wrapped

    def extract: W[U] = {
        checkExistence
        _wrapped.get
    }

    @throws[NullPointerException]
    def checkExistence: Boolean = {
        if(_wrapped.isDefined) true
        else throw new NullPointerException("Failed to extract Wrapper object. Option is empty.")
    }

    def wrapped_=(newWrapped: W[U]) = _wrapped = Option(newWrapped)

    def wrapped_=(newWrapped: Option[W[U]]) = _wrapped = newWrapped

    override def load(o: Any) = {
        //still gotta do
    }
}
