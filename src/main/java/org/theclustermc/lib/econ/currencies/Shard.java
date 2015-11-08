package org.theclustermc.lib.econ.currencies;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of Hub.
 * 
 * Hub can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import org.theclustermc.hub.econ.Currency;

public class Shard implements Currency {
    @Override
    public String getName() {
        return "Shard";
    }

    @Override
    public String getSuffix() {
        return "Shards";
    }

    @Override
    public double getRelationalValue() {
        return 1;
    }
}
