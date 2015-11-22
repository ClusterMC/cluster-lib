package org.clustermc.lib.region

import org.bukkit.block.Block
import org.bukkit.{Bukkit, Location, World}
import org.clustermc.lib.utils.math.{Vector2D, Vector3D}

class Cuboid(private[this] val coords: ((Double, Double, Double), (Double, Double, Double)),
             private var _world: Option[World]) {

    if(_world.isEmpty) {
        world = Bukkit.getWorlds.get(0)
    }
    balanceCoord('all)

    def this(v1: Vector3D, v2: Vector3D) = {
        this(((v1.x, v1.y, v1.z), (v2.x, v2.y, v2.z)),
            None)
    }

    def this(v1: Vector3D, v2: Vector3D, w: Option[World]) = {
        this(((v1.x, v1.y, v1.z), (v2.x, v2.y, v2.z)),
            w)
    }

    def this(loc1: Location, loc2: Location) = {
        this(((loc1.getX, loc1.getY, loc1.getZ), (loc2.getX, loc2.getY, loc2.getZ)),
            Option(loc1.getWorld))
    }

    def this(x1: Double = 0D, x2: Double = 0D,
             y1: Double = 0D, y2: Double = 0D,
             z1: Double = 0D, z2: Double = 0D) = {
        this(((x1, y1, z1), (x2, y2, z2)),
            None)
    }

    def this(c: Cuboid) = {
        this(c.minPoint, c.maxPoint, c._world)
    }

    private var _minPoint: Vector3D = Vector3D(coords._1)
    private var _maxPoint: Vector3D = Vector3D(coords._2)
    private var _minLoc: Location = _minPoint.toLocation(_world)
    private var _maxLoc: Location = _maxPoint.toLocation(_world)

    def minPoint = _minPoint

    def maxPoint = _maxPoint

    def minLocation = _minLoc

    def maxLocation = _maxLoc

    def minPoint_=(min: Vector3D) = {
        _minPoint = min
        balanceCoord('all)
        _minLoc = _minPoint.toLocation(_world)
    }

    def maxPoint_=(max: Vector3D) = {
        _maxPoint = max
        balanceCoord('all)
        _maxLoc = _maxPoint.toLocation(_world)
    }

    def minLocation_=(loc: Location) = {
        _world = Option(loc.getWorld)
        _minPoint = Vector3D(loc.getX, loc.getY, loc.getZ)
        balanceCoord('all)
    }

    def maxLocation_=(loc: Location) = {
        _world = Option(loc.getWorld)
        _maxPoint = Vector3D(loc.getX, loc.getY, loc.getZ)
        balanceCoord('all)
    }

    def world: Option[World] = _world

    def world_=(world: World): Unit = {
        val oWorld = Option(world)
        if(oWorld.isEmpty) return
        _world = oWorld
    }

    override def clone = new Cuboid(this)

    def xDiff = {
        balanceCoord('x)
        _maxPoint.x - _minPoint.x
    }

    def yDiff = {
        balanceCoord('y)
        _maxPoint.y - _minPoint.y
    }

    def zDiff = {
        balanceCoord('z)
        _maxPoint.z - _minPoint.z
    }

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
                        list ::= b1
                        list ::= b2
                    }
                }
            }
        }
        (_minPoint.yInt + 1 until _maxPoint.yInt) foreach {
            y => {
                list ::= w.getBlockAt(_minPoint.xInt, y, _minPoint.zInt)
                list ::= w.getBlockAt(_minPoint.xInt, y, _maxPoint.zInt)
                list ::= w.getBlockAt(_maxPoint.xInt, y, _minPoint.zInt)
                list ::= w.getBlockAt(_maxPoint.xInt, y, _maxPoint.zInt)
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
                                list ::= b
                            }
                        }
                    }
                }
            }
        }
        list
    }

    private def isOnFace(i: Int): Boolean = {
        val __minPoint = _minPoint
        val __maxPoint = _maxPoint
        i match {
            case `__minPoint`.xInt |
                 `__minPoint`.yInt |
                 `__minPoint`.zInt |
                 `__maxPoint`.xInt |
                 `__maxPoint`.yInt |
                 `__maxPoint`.zInt => true
            case _ => false
        }
    }

    private def isOnEdge(vec: Vector2D): Boolean = {
        val __minPoint = _minPoint
        val __maxPoint = _maxPoint
        vec.toInt match {
            case Vector2D(`__minPoint`.xInt, `__minPoint`.zInt) |
                 Vector2D(`__maxPoint`.xInt, `__maxPoint`.zInt) => true
            case _ => false
        }
    }

    private def isOnFace(vec: Vector3D): Boolean = {
        val __minPoint = _minPoint
        val __maxPoint = _maxPoint
        vec.isOnFace(_minPoint, _maxPoint)
        vec.toInt match {
            case Vector3D(`__minPoint`.xInt, _, _) |
                 Vector3D(_, _, `__minPoint`.zInt) |
                 Vector3D(_, `__minPoint`.yInt, _) |
                 Vector3D(`__maxPoint`.xInt, _, _) |
                 Vector3D(_, _, `__maxPoint`.zInt) |
                 Vector3D(_, `__maxPoint`.yInt, _) => true
            case _ => false
        }
    }

    private def isOnEdge(vec: Vector3D): Boolean = {
        val __minPoint = _minPoint
        val __maxPoint = _maxPoint
        vec.toInt match {
            case Vector3D(`__minPoint`.xInt, `__minPoint`.yInt, _) |
                 Vector3D(`__minPoint`.xInt, _, `__minPoint`.zInt) |
                 Vector3D(_, `__minPoint`.yInt, `__minPoint`.zInt) |
                 Vector3D(`__maxPoint`.xInt, `__maxPoint`.yInt, _) |
                 Vector3D(`__maxPoint`.xInt, _, `__maxPoint`.zInt) |
                 Vector3D(_, `__maxPoint`.yInt, `__maxPoint`.zInt) => true
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
                if(_maxPoint.x < _minPoint.x) {
                    val temp = _minPoint.x
                    _minPoint.x = _maxPoint.x
                    _maxPoint.x = temp
                }
                if(_maxPoint.z < _minPoint.y) {
                    val temp = _minPoint.y
                    _minPoint.y = _maxPoint.y
                    _maxPoint.y = temp
                }
                if(_maxPoint.z < _minPoint.z) {
                    val temp = _minPoint.z
                    _minPoint.z = _maxPoint.z
                    _maxPoint.z = temp
                }
            case _ => return
        }
        _minLoc = _minPoint.toLocation(_world)
        _maxLoc = _maxPoint.toLocation(_world)
    }
}

object Cuboid {
    def apply(x1: Double, x2: Double, y1: Double, y2: Double, z1: Double, z2: Double) = {
        new Cuboid(x1, x2, y1, y2, z1, z2)
    }

    def apply(loc1: Location, loc2: Location) = new Cuboid(loc1, loc2)

    def apply(c: Cuboid) = new Cuboid(c)
}
