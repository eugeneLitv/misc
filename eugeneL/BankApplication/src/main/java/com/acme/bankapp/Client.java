/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 24/03/16
 * Time: 18:49
 */
package com.acme.bankapp;

import org.pmw.tinylog.Logger;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

class Client implements Report {

    private final UUID               id;
    private final Map<UUID, Account> accounts = new HashMap<>();

    private String name;
    private UUID   activeAccount = null;
    private float  initialOverdraft = 300f;

    // new Client(name)
    // new Client(name, overdraft)
    // new Client(id, name)
    // new Client(id, name, overdraft)

    public Client(String clientName) {
        this(null, clientName);
    }
    public Client(String clientName, float clientInitialOverdraft) throws Exception {
        this(null, clientName, clientInitialOverdraft);
    }
    public Client(UUID clientId, String clientName) {
        if (clientId != null) {id = clientId;}
        else {id = UUID.randomUUID();}
        name = clientName;
    }
    public Client(UUID clientId, String clientName, float clientInitialOverdraft) throws Exception {
        this(clientId, clientName);
        if ( ! setInitialOverdraft(clientInitialOverdraft) ) {
            String eMsg = "Create new client: Wrong Initial Overdraft: " + clientInitialOverdraft;
            Logger.error(eMsg);
            throw new Exception(eMsg);
        }
    }

    @Override
    public void printReport() {
        Logger.info("=== Client Report ===");
        Logger.info("Client Id: {} Name: {}", id, name);
        Logger.info("initialOverdraft: {}", initialOverdraft);
        if ( accounts.isEmpty() ) {
            Logger.info("The client has no any accounts.");
        } else {
            int num = 0,
                numChecking = 0,
                numSaving = 0,
                numUnknown = 0;

            for (Account a : accounts.values()) {
                num++;
                switch (a.getType()) {
                    case CHECKING: numChecking++;
                        break;
                    case SAVING: numSaving++;
                        break;
                    default:
                        numUnknown++;
                        break;
                }
            }
            Logger.info("Total accounts: {}", num);
            Logger.info("Number of Saving Accounts: {}", numSaving);
            Logger.info("Number of Checking Accounts: {}", numChecking);
            if ( numUnknown > 0 ) { Logger.info("Unknown type accounts: {}", numUnknown);}
            Logger.info("Total Balance: {}", getBalance());
            Logger.info("Accounts:");
            accounts.values().stream().forEach(Account::printReport);
        }
    }

    public UUID setActiveAccount(UUID AccountId) {
        if (accounts.containsKey(AccountId)) {
            activeAccount = AccountId;
            return activeAccount;
        } else {
            Logger.warn("The client (id: {}, name {}) has no account with id: {}.",
                getId(), getName(), AccountId);
            return null;
        }
    }

    public float getBalance() {
        float totalBalance = 0;

        if ( !accounts.isEmpty() ) {
            for (Account a : accounts.values()) totalBalance += a.getBalance();
        }
        return totalBalance;
    }

    public Map<UUID, Account> getAccounts() { return accounts; }

    public boolean deposit(float x) {
        Account account = getActiveAccount();
        return (account != null) && account.deposit(x);
    }

    public boolean withdraw(float x) {
        Account account = getActiveAccount();
        return (account != null) && account.withdraw(x);
    }

    public Account createAccount(UUID accountId, AccountTypes accountType) {
        Account account = null;
        if (accounts.containsKey(accountId)) {
           Logger.warn("The client (id: {}, name {}) already has this account id: {}.",
                getId(), getName(), accountId);
            return null;
        }
        switch (accountType) {
            case CHECKING:
                account = accounts.put(accountId, new CheckingAccount(accountId, initialOverdraft));
                break;
            case SAVING:
                account = accounts.put(accountId, new SavingAccount(accountId));
                break;
            default:
                Logger.warn("Unknown account type: \"{}\"", accountType);
        }
        return account;
    }
    public Account createAccount(AccountTypes accountType) {
        UUID accountId = UUID.randomUUID();
        return createAccount(accountId, accountType);
    }

    public String  getName() { return name; }
    public UUID    getId() { return id; }
    public float   getInitialOverdraft() { return initialOverdraft; }
    public boolean setInitialOverdraft(float overdraft) {
        if (overdraft < 0) { return false; }
        initialOverdraft = overdraft;
        return true;
    }

    public Account getAccountById(UUID accountId) {
        return accounts.get(accountId);
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
