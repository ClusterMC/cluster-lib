package org.theclustermc.lib.econ.currencies;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import org.theclustermc.lib.econ.Wallet;

public class ShardWallet extends Wallet<Shard> {

    public ShardWallet(double amount) {
        super(new Shard(), amount);
    }

    public ShardWallet() {
        super(new Shard());
    }

}
