/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 26/03/16
 * Time: 18:49
 */
package com.acme.bankapp;

import org.pmw.tinylog.Logger;

class CheckingAccount extends AbstractAccount {
    private final static AccountTypes type = AccountTypes.CHECKING;
    private float overdraft = 0;

    public AccountTypes getType() {
        return type;
    }

    @Override
    public void printReport() {
        Logger.info("{} Balance: {} Overdraft: {}", type.getAccountTypeName(), balance, overdraft);
    }

    @Override
    public float getBalance() {
        return balance;
    }

    @Override
    public boolean deposit(float x) {
        if (x <= 0) {
            Logger.info("Cannot deposit zero or negative funds");
            return false;
        }
        if (balance > Float.MAX_VALUE - x) {
            /* overflow: balance + x > Float.MAX_VALUE */
            Logger.info("Balance overflow. You are incredibly rich.");
            return false;
        }

        balance = balance + x;
        return true;
    }

    @Override
    public boolean withdraw(float x) {
        if (x <= 0) {
            Logger.info("Cannot withdraw zero or negative funds");
            return false;
        }

        if (balance < (-Float.MAX_VALUE + x) ) {
            /* underflow: balance - x < -Float.MAX_VALUE */
            Logger.info("Balance underflow. You are incredibly trusted and lucky.");
            Logger.info("balance: {}\nx: {}", balance, x);
            return false;
        }

        final float newBalance = balance - x;
        if ( ( (overdraft > 0 && newBalance > 0) && (overdraft > Float.MAX_VALUE - newBalance) )
             ||
             ( (overdraft < 0 && newBalance < 0) && (overdraft < -Float.MAX_VALUE - newBalance) )
           ) {
            /* overflow when
             * both positive and overdraft + newBalance > Float.MAX_VALUE
             * both negative and overdraft + newBalance < -Float.MAX_VALUE
             */
            Logger.info("NewBalance underflow. You are incredibly trusted and lucky.\nbalance: {}\nnewBalance: {}",
                         balance, newBalance);
            return false;
        }

        if (overdraft + newBalance < 0) {
            Logger.info("Non-sufficient funds to withdraw {} ...", x);
            return false;
        }
        balance = newBalance;
        return true;
    }

    boolean setOverdraft(float x) {
        if (x < 0) {
            Logger.info("Overdraft cannot be negative: {}", x);
            return false;
        }
        overdraft = x;
        return true;
    }

    float getOverdraft() {
        return overdraft;
    }
}
