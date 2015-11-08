package org.theclustermc.lib.utils

import java.util.function.Consumer

import scala.language.implicitConversions

object ClosureImplicits {
    /**
      * Can be used either as a function call or implicit conversion
      * Less verbose if the resulting Consumer doesn't require B lower bounded by A
      *
      * ClosureImplicits.consumer(e => document.append(e.getKey, e.getValue))
      * val closure: Entry[String, AnyRef] = (e: Entry[String, AnyRef]) => document.append(e.getKey, e.getValue)
      *
      * @param f consumer closure
      * @tparam A Lower type bound
      * @tparam B Type lower bounded by A
      * @return java Consumer[B]
      */
    implicit def consumer[A, B >: A](f: (B) => Unit): Consumer[B] = new Consumer[B] {
        override def accept(t: B): Unit = f.apply(t)
    }
}
