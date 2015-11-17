package org.clustermc.lib.region

import org.bukkit.block.Block
import org.bukkit.{Bukkit, Location, World}
import org.clustermc.lib.utils.{Vector2D, Vector3D}

class Cuboid(private[this] val coords: ((Double, Double, Double), (Double, Double, Double)),
             private var _world: Option[World] = Option(Bukkit.getWorlds.get(0))) {

    balanceCoord('all)

    def this(t1: (Double, Double, Double), t2: (Double, Double, Double)) = this((t1, t2))

    def this(v1: Vector3D, v2: Vector3D) = this((v1.x, v1.y, v1.z), (v2.x, v2.y, v2.z))

    def this(loc1: Location, loc2: Location) = {
        this((loc1.getX, loc1.getY, loc1.getZ), (loc2.getX, loc2.getY, loc2.getZ))
    }

    def this(x1: Double = 0D, x2: Double = 0D,
             y1: Double = 0D, y2: Double = 0D,
             z1: Double = 0D, z2: Double = 0D) = {
        this((x1, y1, z1), (x2, y2, z2))
    }

    def this(c: Cuboid) = {
        this(c.minPoint, c.maxPoint)
    }

    private var _minPoint: Vector3D = Vector3D(coords._1)
    private var _maxPoint: Vector3D = Vector3D(coords._2)

    def minPoint = _minPoint

    def maxPoint = _maxPoint

    def minPoint_=(min: Vector3D) = _minPoint = min

    def maxPoint_=(max: Vector3D) = _maxPoint = max

    def world: Option[World] = _world

    def world_=(world: World): Unit = {
        val oWorld = Option(world)
        if(oWorld.isEmpty) return
        _world = oWorld
    }

    override def clone = new Cuboid(this)

    def xDiff = _maxPoint.x - _minPoint.x

    def yDiff = _maxPoint.y - _minPoint.y

    def zDiff = _maxPoint.z - _minPoint.z

    def area = xDiff * zDiff

    def volume = area * yDiff

    def edgeLocations: List[Location] = {
        edgeBlocks map { b: Block => b.getLocation }
    }

    /**
      * This is disgusting.
      * @return list of blocks on cuboid edges
      */
    def edgeBlocks: List[Block] = {
        var list = List[Block]()
        if(_world.isEmpty) return list

        val w = _world.get

        (_minPoint.xInt to _maxPoint.xInt) foreach {
            x => (_minPoint.zInt to _maxPoint.zInt) foreach {
                z => {
                    if(isOnEdge(Vector2D(x, z))) {
                        val b1 = w.getBlockAt(x, _minPoint.yInt, z)
                        val b2 = w.getBlockAt(x, _maxPoint.yInt, z)
                        list = b1 :: b2 :: list
                    }
                }
            }
        }
        (_minPoint.yInt + 1 until _maxPoint.yInt) foreach {
            y => {
                list = w.getBlockAt(_minPoint.xInt, y, _minPoint.zInt) :: list
                list = w.getBlockAt(_minPoint.xInt, y, _maxPoint.zInt) :: list
                list = w.getBlockAt(_maxPoint.xInt, y, _minPoint.zInt) :: list
                list = w.getBlockAt(_maxPoint.xInt, y, _maxPoint.zInt) :: list
            }
        }
        list
    }

    def faceLocations: List[Location] = {
        faceBlocks map { b: Block => b.getLocation }
    }

    def faceBlocks: List[Block] = {
        var list = List[Block]()
        if(_world.isEmpty) return list

        val w = _world.get

        (_minPoint.xInt to _maxPoint.xInt) foreach {
            x => (_minPoint.zInt to _maxPoint.zInt) foreach {
                z => {
                    (_minPoint.yInt to _maxPoint.yInt) foreach {
                        y => {
                            if(isOnFace(Vector3D(x, y, z))) {
                                val b = w.getBlockAt(x, y, z)
                                list = b :: list
                            }
                        }
                    }
                }
            }
        }
        list
    }

    private def isOnFace(i: Int): Boolean = {
        i match {
            case `_minPoint`.xInt |
                 `_minPoint`.yInt |
                 `_minPoint`.zInt |
                 `_maxPoint`.xInt |
                 `_maxPoint`.yInt |
                 `_maxPoint`.zInt => true
            case _ => false
        }
    }

    private def isOnEdge(vec: Vector2D): Boolean = {
        vec.toInt match {
            case Vector2D(`_minPoint`.xInt, `_minPoint`.zInt) => true
            case _ => false
        }
    }

    private def isOnFace(vec: Vector3D): Boolean = {
        vec.toInt match {
            case Vector3D(`_minPoint`.xInt, _, _) |
                 Vector3D(_, _, `_minPoint`.zInt) |
                 Vector3D(_, `_minPoint`.yInt, _) => true
            case _ => false
        }
    }

    private def isOnEdge(vec: Vector3D): Boolean = {
        vec.toInt match {
            case Vector3D(`_minPoint`.xInt, `_minPoint`.yInt, _) |
                 Vector3D(`_minPoint`.xInt, _, `_minPoint`.zInt) |
                 Vector3D(_, `_minPoint`.yInt, `_minPoint`.zInt) |
                 Vector3D(`_maxPoint`.xInt, `_maxPoint`.yInt, _) |
                 Vector3D(`_maxPoint`.xInt, _, `_maxPoint`.zInt) |
                 Vector3D(_, `_maxPoint`.yInt, `_maxPoint`.zInt) => true
            case _ => false
        }
    }

    private def isOnEdge(loc: Location): Boolean = {
        isOnEdge { Vector3D(loc.getBlockX, loc.getBlockY, loc.getBlockZ) }
    }


    private def isOnEdge(block: Block): Boolean = {
        isOnEdge { Vector3D(block.getX, block.getY, block.getZ) }
    }

    private def balanceCoord(s: Symbol): Unit = {
        s match {
            case 'x if _maxPoint.x < _minPoint.x =>
                val temp = _minPoint.x
                _minPoint.x = _maxPoint.x
                _maxPoint.x = temp
            case 'y if _maxPoint.z < _minPoint.y =>
                val temp = _minPoint.y
                _minPoint.y = _maxPoint.y
                _maxPoint.y = temp
            case 'z if _maxPoint.z < _minPoint.z =>
                val temp = _minPoint.z
                _minPoint.z = _maxPoint.z
                _maxPoint.z = temp
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
