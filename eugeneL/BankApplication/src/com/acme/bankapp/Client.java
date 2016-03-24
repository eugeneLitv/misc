package com.acme.bankapp;

/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 24/03/16
 * Time: 18:49
 */
import java.util.*;

class Client implements Report {
    private String        name = "Unnamed Client";
    private List<Account> accounts = null;
    private Account       activeAccount = null;
    private float         initialOverdraft = 300f;

    public Client() { }
    public Client(String n, Float o) {
        if (n != null) {
            name = n;
        }
        if (o != null) {
            initialOverdraft = o.floatValue();
        }
    }
    public Client(String n) {
        this(n, null);
    }
    public Client(Float o) {
        this(null, o);
    }

    @Override
    public void printReport() {
        System.out.println("=== Client Report ===");
        System.out.println("Name: " + name);
        System.out.println("initialOverdraft: " + initialOverdraft);
        if ( accounts != null && !accounts.isEmpty() ) {
            int num = 0,
                num_checking = 0,
                num_saving = 0;

            for (Account a: accounts) {
                num++;
                if (a instanceof CheckingAccount) {
                    num_checking++;
                } else if (a instanceof SavingAccount) {
                    num_saving++;
                }
            }
            System.out.println("Total accounts: " + num);
            System.out.println("Number of Saving Accounts: " + num_saving);
            System.out.println("Number of Checking Accounts: " + num_checking);
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

    public Account createAccount(String accountType) {
        Account a;
        if ("checking".equals(accountType)) {
            CheckingAccount ca = new CheckingAccount();
            ca.setOverdraft(initialOverdraft);
            a = ca;
        } else if ("saving".equals(accountType)) {
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
}
