package org.theclustermc.lib.econ;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of GuildsCore.
 * 
 * GuildsCore can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

public interface Currency {

    /**
     * Gets the full name of this currency.
     * @return The name.
     */
    public String getName();

    /**
     * Gets the string that appears after the value when displayed
     * @return The suffix
     */
    public String getSuffix();

    /**
     * Gets this currency's value in "default currency"
     * Used for having multiple currencies
     */
    public default double getRelationalValue(){
        return 1.0;
    }

}
