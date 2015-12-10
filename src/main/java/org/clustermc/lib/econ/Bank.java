package org.clustermc.lib.econ;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of GuildsCore.
 * 
 * GuildsCore can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import org.clustermc.lib.econ.currencies.ClusterWallet;
import org.clustermc.lib.econ.currencies.ShardWallet;

public class Bank {

    private ShardWallet shardWallet;
    private ClusterWallet clusterWallet;

    public String serialize(){
        return shardWallet.getAmount() + ";" + clusterWallet.getAmount();
    }

    public Bank(String string){
        this();
        String[] serialized = string.split(";");
        shardWallet.setAmount(Double.parseDouble(serialized[0]));
        clusterWallet.setAmount(Double.parseDouble(serialized[1]));
    }

    public Bank(){
        this.shardWallet = new ShardWallet();
        this.clusterWallet = new ClusterWallet();
    }

    public ShardWallet getShardWallet() {return this.shardWallet;}

    public ClusterWallet getClusterWallet() {return this.clusterWallet;}
}
