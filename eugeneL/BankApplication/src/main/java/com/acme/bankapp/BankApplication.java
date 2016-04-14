package com.acme.bankapp;

import java.util.Random;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.writers.ConsoleWriter;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: eugeneL
 * Date: 24/03/16
 * Time: 18:49
 */
public class BankApplication {
    private static final Level[] logLevels = {
        Level.OFF,
        Level.ERROR,
        Level.WARNING,
        Level.INFO,
        Level.DEBUG,
        Level.TRACE
    };
    private static final Level defaultLogLevel = Level.INFO;

    public static void main(String args[]) {

        //Configurator logConfig = Configurator.currentConfig().writer(new ConsoleWriter());
        Configurator logConfig = Configurator.currentConfig();
        logConfig.level(defaultLogLevel).formatPattern("{message}").activate();

        switch (args.length) {
            case 0:
                logConfig.level(Level.INFO);
                break;
            case 2:
                try {
                    if ( "-d".equals(args[0]) ) {
                        int lvl = Integer.parseInt(args[1]);

                        logConfig.level(logLevels[lvl]);
                        Logger.info("Set log level: {}", logLevels[lvl]);
                        logConfig.activate();
                        break;
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {}
             default:
                logConfig.level(Level.INFO).activate();
                Logger.warn("ERROR: Wrong arguments: \"{}\"", String.join(" ", args));
                Logger.warn("       Usage: {} [-d {1-{}}]", BankApplication.class.getName(), logLevels.length - 1);
                System.exit(1);
        }

        Logger.info("Bank Application");
        BankApplication app = new BankApplication();
        Bank bank = new Bank();
        app.test(bank);
    }
    public void initialize(Bank b) {
        Logger.info("========== initialize begin ==========");
        Random r = new Random();
        int numberOfClients = r.nextInt(6);
        Logger.info("numberOfClients; {}", numberOfClients);
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
            Logger.info("Client: \"{}\" numberOfAccounts: {}", i,  numberOfAccounts);
            for (int j = 0; j < numberOfAccounts; j++) {
                if (!c.createAccount(r.nextBoolean() ? AccountTypes.SAVING : AccountTypes.CHECKING).deposit(1000f + r.nextInt(1000))) {
                    Logger.info("Cannot initially deposit account number: {}", j);
                }
            }
        }
        b.addClient(new Client()).createAccount(AccountTypes.SAVING).deposit(1000f);
        b.addClient(new Client(500f)).createAccount(AccountTypes.CHECKING).deposit(1000f);
        Logger.info("========== initialize end ==========");
    }
    public void printBankReport(Bank b) {
        Logger.info("========== printBankReport ==========");
        if (b != null) {
            b.printReport();
        }
    }
    public void modifyBank(Bank b) {
        Logger.info("========== modifyBank =========");
        /**
         * Create some new accounts
         */
        b.addClient(new Client("Additional Client 1"));
        b.addClient(new Client("Additional Client 2", 400f));
        /**
         * Withdraw
         */
        b.getClients().stream().filter(c -> c.getAccounts() != null).forEach(c -> {
            Logger.info("Client: {}:", c.getName());
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
