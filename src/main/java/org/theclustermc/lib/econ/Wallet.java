package org.theclustermc.lib.econ;
/*
 * Copyright (C) 2013-Current Carter Gale (Ktar5) <buildfresh@gmail.com>
 * 
 * This file is part of GuildsCore.
 * 
 * GuildsCore can not be copied and/or distributed without the express
 * permission of the aforementioned owner.
 */

import lombok.Getter;

public @Getter
abstract class Wallet<C extends Currency> {

    private C currency;
    private double amount;

    /**
     * Creates a new Wallet with the given currency.
     * @param currency The currency.
     */
    public Wallet(C currency){
        this(currency, 0);
    }

    /**
     * Creates a new wallet with the given currency and initial amount.
     * @param currency The currency.
     * @param amount The amount.
     */
    public Wallet(C currency, double amount){
        this.currency = currency;
        this.amount = amount;
    }

    /**
     * Sets the amount in this bank.
     * @param amount The amount.
     * @return False if the amount is not allowed, and true otherwise.
     */
    public boolean setAmount(double amount){
        if(amount < 0){
            return false;
        }
        this.amount = amount;
        return true;
    }

    /**
     * Deposits a given amount of currency into the bank.
     * @param amount The amount.
     * @return False if the amount is not allowed, and true otherwise.
     */
    public boolean deposit(double amount){
        return setAmount(getAmount()+amount);
    }

    /**
     * Withdraws a given amount from this bank. If this class
     * @param amount The amount.
     * @return False if the amount is not allowed, and true otherwise.
     */
    public boolean withdraw(double amount){
        return setAmount(getAmount()-amount);
    }

    /**
     * @param amount The amount to check
     * @return False if bank doesnt have enough money
     */
    public boolean has(double amount){
        return amount >= getAmount();
    }

}
