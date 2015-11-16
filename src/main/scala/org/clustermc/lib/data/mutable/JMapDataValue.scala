package org.clustermc.lib.data.mutable

import java.util

import org.clustermc.lib.data.WrappedDataValueImpl

class JMapDataValue[K,V](private[this] val _wrapped:Option[util.Map[K,V]],
                         val kClass: Class[K], val vClass: Class[V])
extends WrappedDataValueImpl[]{

}
