/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 24/03/16
 * Time: 18:49
 */
package com.acme.bankapp;

import org.pmw.tinylog.Logger;
import java.util.ArrayList;
import java.util.List;

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
            Logger.info("Client Name can not be null. Set Client Name to default \"{}\"", defaultName);
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
        Logger.info("=== Client Report ===");
        Logger.info("Name: {}", name);
        Logger.info("initialOverdraft: {}", initialOverdraft);
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
            Logger.info("Total accounts: {}", num);
            Logger.info("Number of Saving Accounts: {}", numSaving);
            Logger.info("Number of Checking Accounts: {}", numChecking);
            Logger.info("Total Balance: {}", getBalance());
            Logger.info("Accounts:");
            accounts.forEach(Account::printReport);
        } else {
            Logger.info("The client has no any accounts.");
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
            Logger.info("Unknown account type: \"{}\"", accountType);
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
