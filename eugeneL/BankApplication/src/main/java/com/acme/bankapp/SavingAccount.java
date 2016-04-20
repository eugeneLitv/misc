package com.acme.bankapp;

import org.pmw.tinylog.Logger;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 26/03/16
 * Time: 18:49
 */
class SavingAccount extends AbstractAccount {
    private static final AccountTypes type = AccountTypes.SAVING;

    public SavingAccount() {}
    public SavingAccount(UUID accountId) { super(accountId); }

    @Override
    public AccountTypes getType() {
        return type;
    }

    @Override
    public void printReport() {
        Logger.info("{} Balance: {}", type, balance);
    }

    @Override
    public float getBalance() {
        return balance;
    }

    @Override
    public boolean deposit(float x) {
        balance = balance + x;
        return true;
    }

    @Override
    public boolean withdraw(float x) {
        if (balance >= x) {
            balance = balance - x;
            return true;
        }
        Logger.info("Non-sufficient funds to withdraw {} ...", x);
        return false;
    }
}
