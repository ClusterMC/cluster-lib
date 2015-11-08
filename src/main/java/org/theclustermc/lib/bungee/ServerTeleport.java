package org.theclustermc.lib.bungee;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.theclustermc.hub.Hub;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerTeleport {

    /**
     * Teleports a player to a server with no checks
     *
     * @param p player to teleport
     * @param server server to go to
     * @return was it successful?
     */
    public static boolean tpToServer(final Player p, String server){
        Bukkit.getScheduler().scheduleSyncDelayedTask(Hub.instance(), () -> {
            if (p != null) {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                try {
                    out.writeUTF("Connect");
                    out.writeUTF(server);
                } catch (IOException e) {e.printStackTrace(); }
                p.sendPluginMessage(Hub.instance(), "BungeeCord", b.toByteArray());
            }
        }, 2);
        return true;
    }

}
