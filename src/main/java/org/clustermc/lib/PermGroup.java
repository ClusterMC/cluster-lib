package org.clustermc.lib;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

public enum PermGroup {
    MEMBER("", "&7", "&e&l", "&7"),
    HELPER("&a*", "&7", "&e&l", "&7"),
        SAGA("&9&lSAGA", "&f", "&9&l", "&7"),
        EPIC("&a&lEPIC", "&f", "&a&l", "&7"),
        MYTHIC("&6&lMYTHIC", "&f", "&6&l", "&f"),
    CONTENT("&2&lCONTENT", "&7", "&2&l", "&7"),
    MOD("&3&lMOD", "&f", "&3&l", "&f"),
    MOD_PLUS("&b&lMOD", "&f", "&b&l", "&f"),
    ADMIN("&c&lADMIN", "&f", "&c&l", "&f"),
    NETADMIN("&c&lADMIN", "&f", "&c&l", "&f"),
    OWNER("&5&lOWNER", "&f", "&5&l", "&f&l");

    public String prefix, player, suffix, chat;

    PermGroup(String prefix, String player, String suffix, String chat) {
        this.prefix = prefix;
        this.player = player;
        this.suffix = suffix;
        this.chat = chat;
    }
}
