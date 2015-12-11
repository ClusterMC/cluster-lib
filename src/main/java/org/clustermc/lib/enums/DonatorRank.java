package org.clustermc.lib.enums;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

public enum DonatorRank {
    NONE(new RankStrings("", "", "", "")),
    SAGA(new RankStrings("&9&lSAGA", "&f", "&9&l", "&7&o")),
    EPIC(new RankStrings("&a&lEPIC", "&f", "&a&l", "&7&o")),
    MYTHIC(new RankStrings("&6&lMYTHIC", "&f", "&6&l", "&f&o"));

    public RankStrings strings;

    DonatorRank(RankStrings rankStrings) {
        this.strings = rankStrings;
    }

    public static boolean contains(String string){
        return string.equalsIgnoreCase("NONE") ||
                string.equalsIgnoreCase("SAGA") ||
                string.equalsIgnoreCase("EPIC") ||
                string.equalsIgnoreCase("MYTHIC");
    }
}
