package org.clustermc.lib.utils

import scala.reflect.ClassTag

object GenericOps {

    implicit class AsOpt[A](val a: A) extends AnyVal {
        def asOpt[B <: A : ClassTag]: Option[B] = a match {
            case b: B => Some(b)
            case _    => None
        }
    }
}

