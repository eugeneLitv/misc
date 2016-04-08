/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 26/03/16
 * Time: 18:49
 */
package com.acme.bankapp;

class CheckingAccount extends AbstractAccount {
    private final static AccountTypes type = AccountTypes.CHECKING;
    private float overdraft = 0;

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
        if (x <= 0) {
            System.out.println("Cannot deposit zero or negative funds");
            return false;
        }
        if (balance > Float.MAX_VALUE - x) {
            /* overflow: balance + x > Float.MAX_VALUE */
            System.out.println("Balance overflow. You are incredibly rich.");
            return false;
        }

        balance = balance + x;
        return true;
    }

    @Override
    public boolean withdraw(float x) {
        if (x <= 0) {
            System.out.println("Cannot withdraw zero or negative funds");
            return false;
        }

        if (balance < (-Float.MAX_VALUE + x) ) {
            /* underflow: balance - x < -Float.MAX_VALUE */
            System.out.println("Balance underflow. You are incredibly trusted and lucky.");
            System.out.println("balance: " + balance
                              + "\nx: " + x);
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
            System.out.println("NewBalance underflow. You are incredibly trusted and lucky.");
            System.out.println("balance: " + balance + "\nnewBalance: " + newBalance);
            return false;
        }

        if (overdraft + newBalance < 0) {
            System.out.println("Non-sufficient funds to withdraw " + x + " ...");
            return false;
        }
        balance = newBalance;
        return true;
    }

    boolean setOverdraft(float x) {
        if (x < 0) {
            System.out.println("Overdraft cannot be negative: " + x);
            return false;
        }
        overdraft = x;
        return true;
    }

    float getOverdraft() {
        return overdraft;
    }
}
