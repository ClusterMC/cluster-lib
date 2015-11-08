package org.theclustermc.lib.utils.cooldown;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of GuildsCore.
 * 
 * GuildsCore can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import lombok.Getter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class CooldownHandler {

    @Getter
    private HashMap<UUID, Coolection> cooldownPlayers;

    public CooldownHandler() {
        cooldownPlayers = new HashMap<>();
    }

    public void add(UUID player, String ability, double seconds, CooldownExecutor executor) {
        if (!cooldownPlayers.containsKey(player)){
            cooldownPlayers.put(player, new Coolection(player));
        }else if(isCooling(player, ability)){
            return;
        }
        cooldownPlayers.get(player).add(ability.toLowerCase(), seconds, executor);
    }

    public void add(UUID player, String ability, double seconds){
        if (!cooldownPlayers.containsKey(player)){
            cooldownPlayers.put(player, new Coolection(player));
        }else if(isCooling(player, ability)){
            return;
        }
        cooldownPlayers.get(player).add(ability.toLowerCase(), seconds);
    }

    public boolean isCooling(UUID player, String ability) {
        Coolection cool = cooldownPlayers.get(player);
        return cool != null && cool.isCooling(ability);
    }

    public void handleCooldowns() {
        if (cooldownPlayers.isEmpty()) {
            return;
        }
        Iterator<Coolection> iterator = cooldownPlayers.values().iterator();
        while(iterator.hasNext()){
            if(iterator.next().handleCooldowns()){
                iterator.remove();
            }
        }
    }

}
