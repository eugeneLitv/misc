/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 24/03/16
 * Time: 18:49
 */
package com.acme.bankapp;

import java.util.*;

class Client implements Report {
    private final static String defaultName = "Unnamed Client";

    private String        name;
    private List<Account> accounts = null;
    private Account       activeAccount = null;
    private float         initialOverdraft = 300f;

    public Client() {
        name = defaultName;
    }
    public Client(String n, float o) {
        name = n != null ? n : defaultName;
        initialOverdraft = o;
    }
    public Client(String n) {
        if (n == null) {
            System.out.println("Client Name can not be null. Set Client Name to default \"" + defaultName + "\"");
            name = defaultName;
            return;
        }
        name = n;
    }
    public Client(float o) {
        this(null, o);
    }

    @Override
    public void printReport() {
        System.out.println("=== Client Report ===");
        System.out.println("Name: " + name);
        System.out.println("initialOverdraft: " + initialOverdraft);
        if ( accounts != null && !accounts.isEmpty() ) {
            int num = 0,
                numChecking = 0,
                numSaving = 0;

            for (Account a: accounts) {
                num++;
                if (a instanceof CheckingAccount) {
                    numChecking++;
                } else if (a instanceof SavingAccount) {
                    numSaving++;
                }
            }
            System.out.println("Total accounts: " + num);
            System.out.println("Number of Saving Accounts: " + numSaving);
            System.out.println("Number of Checking Accounts: " + numChecking);
            System.out.println("Total Balance: " + getBalance());
            System.out.println("Accounts:");
            accounts.forEach(Account::printReport);
        } else {
            System.out.println("The client has no any accounts.");
        }
    }

    public void setActiveAccount(Account a) {
        activeAccount = a;
    }

    public float getBalance() {
        float total_balance = 0;
        if (accounts != null && !accounts.isEmpty()) {
            for (Account a : accounts) total_balance += a.getBalance();
        }
        return total_balance;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public boolean deposit(float x) {
        return (activeAccount != null) && activeAccount.deposit(x);
    }

    public boolean withdraw(float x) {
        return (activeAccount != null) && activeAccount.withdraw(x);
    }

    public Account createAccount(AccountTypes accountType) {
        Account a;
        if (accountType == AccountTypes.CHECKING) {
            CheckingAccount ca = new CheckingAccount();
            ca.setOverdraft(initialOverdraft);
            a = ca;
        } else if (accountType == AccountTypes.SAVING) {
            a = new SavingAccount();
        } else {
            System.out.println("Unknown account type: \"" + accountType + "\"");
            return null;
        }
        if (accounts == null) {
            accounts = new ArrayList<Account>();
            activeAccount = a; // when the very first account is added set activeAccount to it
        }
        accounts.add(a);
        return a;
    }

    public String getName() {
        return name;
    }

    public float getInitialOverdraft() {
        return initialOverdraft;
    }
}
