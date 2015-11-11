package org.clustermc.lib.utils.cooldown;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import org.bukkit.entity.Player;

public interface CooldownExecutor {
    void use(Player player, String ability);
}
