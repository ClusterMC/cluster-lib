package org.clustermc.lib.region

import java.lang.Math._

import org.bukkit.Location

class Cuboid(private var _x1: Double = 0D,
             private var _x2: Double = 0D,
             private var _y1: Double = 0D,
             private var _y2: Double = 0D,
             private var _z1: Double = 0D,
             private var _z2: Double = 0D) {

    balanceCoord('all)

    def this(loc1: Location, loc2: Location) = {
        this(loc1.getX, loc2.getX, loc1.getY, loc2.getY, loc1.getZ, loc2.getZ)
    }

    def this(c: Cuboid) = {
        this(c._x1, c._x2, c._y1, c._y2, c._z1, c._z2)
    }

    override def clone = new Cuboid(this)

    def x1 = _x1

    def x2 = _x2

    def y1 = _y1

    def y2 = _y2

    def z1 = _z1

    def z2 = _z2

    def xDiff = max(_x1, _x2) - min(_x1, _x2)

    def yDiff = max(_y1, _y2) - min(_y1, _y2)

    def zDiff = max(_z1, _z2) - min(_z1, _z2)

    def area = xDiff * zDiff

    def volume = area * yDiff

    def expand(x: Double = 0, y: Double = 0, z: Double = 0) = {
        _x2 += x
        _y2 += y
        _z2 += z
        balanceCoord('all)
    }

    def shrink(x: Double = 0, y: Double = 0, z: Double = 0) = {
        _x2 -= x
        _y2 -= y
        _z2 -= z
        balanceCoord('all)
    }

    def scale(x: Double = 0, y: Double = 0, z: Double = 0) = {
        _x2 *= x
        _y2 *= y
        _z2 *= z
        balanceCoord('all)
    }

    private def balanceCoord(s: Symbol): Unit = {
        s match {
            case 'x if _x2 < _x1 =>
                val temp = _x1
                _x1 = _x2
                _x2 = temp
            case 'y if _y2 < _y1 =>
                val temp = _y1
                _y1 = _y2
                _y2 = temp
            case 'z if _z2 < _z1 =>
                val temp = _z1
                _z1 = _z2
                _z2 = temp
            case 'all =>
                balanceCoord('x)
                balanceCoord('y)
                balanceCoord('z)
        }
    }
}

object Cuboid {
    def apply(x1: Double, x2: Double, y1: Double, y2: Double, z1: Double, z2: Double) = {
        new Cuboid(x1, x2, y1, y2, z1, z2)
    }

    def apply(loc1: Location, loc2: Location) = new Cuboid(loc1, loc2)

    def apply(c: Cuboid) = new Cuboid(c)
}
