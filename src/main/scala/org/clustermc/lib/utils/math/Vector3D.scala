package org.clustermc.lib.utils.math

import org.bukkit.{Location, World}

case class Vector3D(var x: Double, var y: Double, var z: Double) extends Cloneable {
    val xInt = x.toInt
    val yInt = y.toInt
    val zInt = z.toInt

    def toInt = Vector3D(xInt, yInt, zInt)

    @throws[IllegalArgumentException]
    def toLocation(w: Option[World]) = {
        if(w.isEmpty)      {
            throw new IllegalArgumentException("Cannot convert Vector3D to Location with an empty World Option")
        }
        new Location(w.get, x, y, z)
    }

    def toLocation(w: World) = {
        new Location(w, x, y, z)
    }

    override def clone(): Vector3D = {
        Vector3D(x, y, z)
    }
}

object Vector3D {
    def apply(tuple: (Double, Double, Double)): Vector3D = {
        Vector3D(tuple._1, tuple._2, tuple._3)
    }
}
