package org.theclustermc.lib.utils.cooldown;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of GuildsCore.
 * 
 * GuildsCore can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import org.bukkit.entity.Player;

public class Cooldown{

    private long startStamp;
    private long endStamp;
    private CooldownExecutor executor;

    /**
     * @param seconds must be in quarters: 1.0 - 1.25 - 1.5 - 1.75....
     */
    protected Cooldown(double seconds){
        if(seconds % .25 != 0){
            return;
        }
        startStamp = System.currentTimeMillis() / 250;
        endStamp = (long) (startStamp + (seconds / .25));
    }

    protected Cooldown(double seconds, CooldownExecutor executor){
        this(seconds);
        this.executor = executor;
    }

    protected void execute(Player player, String ability){
        executor.use(player, ability);
    }

    public boolean isFinished(){
        return (System.currentTimeMillis() / 250) >= endStamp;
    }

    public int timeLeft(){
        return (short) ((System.currentTimeMillis() / 250) - startStamp);
    }

}
