/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 26/03/16
 * Time: 18:49
 */
package com.acme.bankapp;

class CheckingAccount extends AbstractAccount {
    final static AccountTypes type = AccountTypes.CHECKING;
    float overdraft = 0;

    public AccountTypes getType() {
        return type;
    }

    @Override
    public void printReport() {
        System.out.println(type.getAccountTypeName() + " Balance: " + balance + " Overdraft: " + overdraft);
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
        float new_balance = balance - x;
        if (overdraft + new_balance >= 0) {
            balance = new_balance;
            return true;
        }
        System.out.println("Non-sufficient funds to withdraw " + x + " ...");
        return false;
    }

    boolean setOverdraft(float x) {
        overdraft = x;
        return true;
    }

    float getOverdraft() {
        return overdraft;
    }
}
