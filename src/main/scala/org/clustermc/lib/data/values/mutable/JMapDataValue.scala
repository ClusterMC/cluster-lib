package org.clustermc.lib.data.values.mutable

import java.util

class JMapDataValue[K,V](private[this] val _wrapped:Option[util.Map[K,V]],
                         val kClass: Class[K], val vClass: Class[V]) {

}
