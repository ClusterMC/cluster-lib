package org.clustermc.lib.achievements;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import org.bukkit.Bukkit;
import org.bukkit.Location;

public enum LocationAchievements {
    BUMBLEBEE(new Location(Bukkit.getWorld("world"), 1, 2, 3),
            5,
            "Rich's Happy Place",
            "Why did we do this? No one knows!");

    LocationAchievements(Location world, int radius, String name, String wittysubtitle) {

    }
}
