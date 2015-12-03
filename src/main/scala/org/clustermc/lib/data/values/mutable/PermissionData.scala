package org.clustermc.lib.data.values.mutable

class PermissionData
(key: String, private val _identifier: Option[Symbol] = None, private val _has: Option[Boolean] = None)
    extends MutableDataValue[Boolean](key, _has, classOf[Boolean]) {

    def permission = _identifier match {
        case Some(s) => s
        case _ => ""
    }

    /**
      * Default return is false if _permission exists
      * Default true if _permission is None or is ""
      *
      * @return has permission or not
      */
    def has = {
        if(_identifier.isEmpty || _identifier.get.equals(Symbol(""))) {
            true
        } else {
            _value match {
                case Some(bool) => bool
                case _ => false
            }
        }
    }


}

object PermissionData {

    def apply(key: String, identifier: Symbol, has: Boolean): PermissionData = apply(key, Option(identifier), Option(has))

    def apply(key: String, identifier: Option[Symbol], has: Option[Boolean]) = new PermissionData(key, identifier, has)
}
