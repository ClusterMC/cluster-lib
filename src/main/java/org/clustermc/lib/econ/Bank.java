package org.clustermc.lib.econ;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of GuildsCore.
 * 
 * GuildsCore can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import lombok.Getter;
import org.bson.Document;
import org.clustermc.lib.econ.currencies.ShardWallet;
import org.clustermc.lib.exceptions.InvalidMongoDocException;
import org.clustermc.lib.econ.currencies.ClusterWallet;
import org.clustermc.lib.utils.database.MongoLoadable;

public class Bank implements MongoLoadable {

    @Getter private ShardWallet shardWallet;
    @Getter private ClusterWallet clusterWallet;

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

    @Override
    public Document toDocument() {
        return null;
    }

    @Override
    public void load(Document doc) throws InvalidMongoDocException {

    }
}
