package org.clustermc.lib.utils

case class Vector3D(var x: Double, var y: Double, var z: Double) {
    val xInt = x.toInt
    val yInt = y.toInt
    val zInt = z.toInt

    def toInt = Vector3D(xInt, yInt, zInt)
}

object Vector3D {
    def apply(tuple: (Double, Double, Double)) = {
        Vector3D(tuple._1, tuple._2, tuple._3)
    }
}
