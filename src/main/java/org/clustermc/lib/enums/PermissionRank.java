package org.clustermc.lib.enums;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

public enum PermissionRank {
    MEMBER(new RankStrings("", "&7", "&f&l", "&7")),
    HELPER(new RankStrings("&a@&l@", "&7", "&f&l", "&7")),
    CONTENT(new RankStrings("&2&lCONTENT", "&7", "&2&l", "&7")),
    MOD(new RankStrings("&3&lMOD", "&f", "&3&l", "&f")),
    MOD_PLUS(new RankStrings("&b&lMOD", "&f", "&b&l", "&f")),
    ADMIN(new RankStrings("&c&lADMIN", "&f", "&c&l", "&f")),
    NETADMIN(new RankStrings("&c&lADMIN", "&f", "&c&l", "&f")),
    OWNER(new RankStrings("&5&lOWNER", "&f", "&5&l", "&f&l"));

    public RankStrings strings;

    PermissionRank(RankStrings rankStrings) {
        this.strings = rankStrings;
    }

    public static boolean contains(String string){
        return string.equalsIgnoreCase("MEMBER") ||
                string.equalsIgnoreCase("HELPER") ||
                string.equalsIgnoreCase("CONTENT") ||
                string.equalsIgnoreCase("MOD") ||
                string.equalsIgnoreCase("MOD_PLUS") ||
                string.equalsIgnoreCase("ADMIN") ||
                string.equalsIgnoreCase("NETADMIN") ||
                string.equalsIgnoreCase("OWNER");
    }
}
