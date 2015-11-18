package org.clustermc.lib.utils.implicits

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

object PlayerImplicits {
    implicit class RichPlayer(val p: Player) {
        def clearInventory(): Unit = {
            p.getInventory.clear()
        }
    }
    implicit class RichInventory(val inv: Inventory) {
        def apply(x: (Inventory => Unit)*): Unit = {
            x foreach { _.apply(inv) }
        }
    }

    implicit class UnknownTypeApply[T](val o: T) {
        def applyFunc[R](x: T => R): R = x apply o
        def applyConsumer(x: T => Unit) = x apply o
    }
}
