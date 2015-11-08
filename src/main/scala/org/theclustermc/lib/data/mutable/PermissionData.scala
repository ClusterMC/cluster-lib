package org.theclustermc.lib.data.mutable


sealed class PermissionData
(private val _identifier: Option[Symbol] = None, private val _has: Option[Boolean] = None)
    extends MutableDataValue[Boolean](_has, classOf[Boolean]) {

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
    import org.theclustermc.lib.utils.GenericOps.option

    def apply(identifier: Symbol, has: Boolean): PermissionData = apply(option(identifier), option(has))

    def apply(identifier: Option[Symbol], has: Option[Boolean]) = new PermissionData(identifier, has)
}
