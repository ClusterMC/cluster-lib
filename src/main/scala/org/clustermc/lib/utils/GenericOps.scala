package org.clustermc.lib.utils

import java.util.Calendar
import java.util.logging.Level

import scala.reflect._

object GenericOps {


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

    val unconverted = "Test"
    val converted = unconverted.asOpt[String]
    val dblConverted = converted.asOpt[Option[String]]
    println(unconverted)
    println(converted)
    println(dblConverted)

    /*
    [WARNING] [Fri Nov 13 21:22:34 HST 2015] Wrapping Option within an Option.
        If this is not desired effect, remove the implicit function call.
    Test
    Some(Test)
    Some(Some(Test))
     */

    def main(args: Array[String]): Unit = {

    }
}

