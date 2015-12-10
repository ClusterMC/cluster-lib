package org.clustermc.lib.utils.cooldown;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of GuildsCore.
 * 
 * GuildsCore can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class CooldownFinishEvent extends Event {

    private UUID player;
    private String ability;

    public CooldownFinishEvent(UUID player, String ability) {
        this.player = player;
        this.ability = ability;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }

    public UUID getPlayer() {return this.player;}

    public String getAbility() {return this.ability;}
}
