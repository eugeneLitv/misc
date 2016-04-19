package com.acme.bankapp;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 26/03/16
 * Time: 18:49
 */
interface Account extends Report {
    UUID getId();
    AccountTypes getType();
    float getBalance();
    boolean deposit(float x);
    boolean withdraw(float x);
}
