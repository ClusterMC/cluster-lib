package org.clustermc.lib.utils.implicits

import java.util.Calendar
import java.util.logging.Level

import scala.reflect._

object GenericImplicits {
    implicit class AsOpt[A](val a: A) extends AnyVal {
        def asOpt[B <: A : ClassTag]: Option[B] = {
            if(classOf[Option[_]].isAssignableFrom(a.getClass)) {
                System.out.println(s"[${Level.WARNING}] [${Calendar.getInstance().getTime}] " +
                    "Wrapping Option within an Option. " +
                    "If this is not desired effect, remove the implicit function call.")
            }
            a match {
                case b: B => Option(b)
                case _ => None
            }
        }
    }
}

