package com.acme.bankapp;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: eugeneL
 * Date: 24/03/16
 * Time: 18:49
 */
public class BankApplication {

    public static void main(String args[]) {
        System.out.println("Bank Application");
        BankApplication app = new BankApplication();
        Bank bank = new Bank();
        app.test(bank);
    }
    public void initialize(Bank b) {
        System.out.println("========== initialize begin ==========");
        Random r = new Random();
        int numberOfClients = r.nextInt(6);
        System.out.println("numberOfClients; " + numberOfClients);
        float initialOverdraft;// = 200f + r.nextInt(200);
        int numberOfAccounts;
        Client c;
        for (int i = 0; i < numberOfClients; i++) {
            initialOverdraft = 200f + r.nextInt(200);
            if (initialOverdraft < 300) {
                c = b.addClient(new Client("Client " + i));
            } else {
                c = b.addClient(new Client("Client " + i, initialOverdraft));
            }
            numberOfAccounts = r.nextInt(10);
            System.out.println("Client: \"" + i + "\" numberOfAccounts: " + numberOfAccounts);
            for (int j = 0; j < numberOfAccounts; j++) {
                if (!c.createAccount(r.nextBoolean() ? AccountTypes.SAVING : AccountTypes.CHECKING).deposit(1000f + r.nextInt(1000))) {
                    System.out.println("Cannot initially deposit account number: " + j);
                }
            }
        }
        b.addClient(new Client()).createAccount(AccountTypes.SAVING).deposit(1000f);
        b.addClient(new Client(500f)).createAccount(AccountTypes.CHECKING).deposit(1000f);
        System.out.println("========== initialize end ==========");
    }
    public void printBankReport(Bank b) {
        System.out.println("========== printBankReport ==========");
        if (b != null) {
            b.printReport();
        }
    }
    public void modifyBank(Bank b) {
        System.out.println("========== modifyBank =========");
        /**
         * Create some new accounts
         */
        b.addClient(new Client("Additional Client 1"));
        b.addClient(new Client("Additional Client 2", 400f));
        /**
         * Withdraw
         */
        b.getClients().stream().filter(c -> c.getAccounts() != null).forEach(c -> {
            System.out.print("Client: " + c.getName() + " :");
            c.withdraw(1800f);
        });
        /**
         * Deposit
         */
        b.getClients().stream().forEach((Client c) -> c.deposit(500f));
    }
    public void test(Bank b) {
        /**
         * TODO: test must be repeatable - do not use Random(),
         * use some array of predefined values for client names, balance, etc
         */
        initialize(b);
        printBankReport(b);
        modifyBank(b);
        printBankReport(b);
    }
}
