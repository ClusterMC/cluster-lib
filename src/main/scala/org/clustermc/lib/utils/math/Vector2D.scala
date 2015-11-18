package org.clustermc.lib.utils.math

case class Vector2D(var x: Double, var z: Double) {
    def xInt = x.toInt
    def zInt = z.toInt

    def toInt = Vector2D(xInt, zInt)
}

object Vector2D {
    def apply(tuple: (Double, Double)): Vector2D = {
        Vector2D(tuple._1, tuple._2)
    }
}
