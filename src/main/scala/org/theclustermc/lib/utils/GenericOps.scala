package org.theclustermc.lib.utils

import scala.reflect.ClassTag

object GenericOps {

    def option[A: ClassTag](x: A): Option[A] = x match {
        case Some(a: A) => Some(a)
        case a: A => Some(a)
        case _ => None
    }
}

