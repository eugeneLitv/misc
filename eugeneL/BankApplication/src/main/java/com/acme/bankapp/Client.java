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
import java.util.UUID;

class Client implements Report {

    private final UUID          id;
    private final List<Account> accounts = new ArrayList<>();

    private String name;
    private UUID   activeAccount = null;
    private float  initialOverdraft = 300f;

    public Client(UUID clientId) {
        id = clientId;
    }
    public Client(UUID clientId, String clientName, float defaultOverdraft) {
        this(clientId);
        name = clientName;
        initialOverdraft = defaultOverdraft;
    }
    public Client(UUID clientId, String clientName) {
        this(clientId);
        name = clientName;
    }
    public Client(UUID clientId, float defaultOverdraft) {
        this(clientId);
        initialOverdraft = defaultOverdraft;
    }

    @Override
    public void printReport() {
        Logger.info("=== Client Report ===");
        Logger.info("Client Id: {} Name: {}", id, name);
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
            accounts.stream().forEach(Account::printReport);
        } else {
            Logger.info("The client has no any accounts.");
        }
    }

    public UUID setActiveAccount(UUID pAccountId) {
        activeAccount = pAccountId;
    }

    public float getBalance() {
        float totalBalance = 0;
        if (accounts != null && !accounts.isEmpty()) {
            for (Account a : accounts) totalBalance += a.getBalance();
        }
        return totalBalance;
    }

    public List<Account> getAccounts() { return accounts; }

    public boolean deposit(float x) {
        Account account = getActiveAccount();
        return (account != null) && account.deposit(x);
    }

    public boolean withdraw(float x) {
        Account account = getActiveAccount();
        return (account != null) && account.withdraw(x);
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

    public String getName() { return name; }
    public UUID   getId() { return id; }
    public float  getInitialOverdraft() { return initialOverdraft; }

  /**
   * We can get zero, one or more than one account,
   * but the function can return only null or Account.
   * So it is impossible to distinguish all these three cases.
   * Either "public Account[] getAccountsById(...)"
   * or modify the List "private final List<Account> accounts = new ArrayList<>();"
   *
   * @param accountId
   * @return
   */
    public Account getAccountById(UUID accountId) {
        int  numOfAccounts = 0;
        Account account = null;

        if (accounts == null || accounts.isEmpty()) return null;

        for (Account a : accounts) {
            if (a.getId() == accountId) {
                account = a;
                numOfAccounts++;
            }
        }
        if (numOfAccounts != 0 && numOfAccounts != 1) {
            // More than one account with the same ID.
            Logger.warn("The client (id: {}, name {}) has {} accounts with the same id ({}).",
                         getId(), getName(), numOfAccounts, accountId);
            return null;
        }
        return account;
    }
    public Account getActiveAccount() {
        if (accounts == null) {
            Logger.warn("The client (id: {}, name {}) has no any accounts.", getId(), getName());
            return null;
        }
        if (activeAccount == null) {
            Logger.warn("Active account is not set.");
            Logger.warn("Set the Client's active account OR provide account id to deposit.");
            return null;
        }
        Account account = getAccountById(activeAccount);
        if (account == null) {
            Logger.warn("Id of Account (id: {}) is set as an active account for the client (id: {} name: {})",
                activeAccount, getId(), getName());
            Logger.warn("But the client does not own account with such id ({}).", activeAccount);
            return null;
        }
        return account;
    }
}
