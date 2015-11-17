package org.clustermc.lib.utils

import org.bukkit.entity.Player

object PlayerImplicits {
    implicit class ClearPlayerInv(val p: Player) extends AnyVal {
        def clearInventory(): Unit = {
            p.getInventory.clear()
        }
    }
}
