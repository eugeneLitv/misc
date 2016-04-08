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
        for (int i_client=1; i_client <= numberOfClients; i_client++) {
            initialOverdraft = 200f + r.nextInt(200);
            if (initialOverdraft < 300) {
                c = b.addClient(new Client("Client " + i_client));
            } else {
                c = b.addClient(new Client("Client " + i_client, initialOverdraft));
            }
            numberOfAccounts = r.nextInt(10);
            System.out.println("Client: \"" + i_client + "\" numberOfAccounts: " + numberOfAccounts);
            for (int i_acc = 1; i_acc <= numberOfAccounts; i_acc++) {
                if (!c.createAccount(r.nextBoolean() ? AccountTypes.SAVING : AccountTypes.CHECKING).deposit(1000f + r.nextInt(1000))) {
                    System.out.println("Cannot initially deposit account number: " + i_acc);
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
        b.getClients().stream().forEach(c -> {c.deposit(500f);});
    }
    public void test(Bank b) {
        initialize(b);
        printBankReport(b);
        modifyBank(b);
        printBankReport(b);
    }
}
