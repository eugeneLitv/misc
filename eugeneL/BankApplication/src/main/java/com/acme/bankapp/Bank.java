package com.acme.bankapp;

/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 26/03/16
 * Time: 18:49
 */
import org.pmw.tinylog.Logger;

import java.util.List;
import java.util.ArrayList;

class Bank implements Report {
    private String name = "ACME Bank";
    private List<Client> clients = null;

    public Bank() {}
    public Bank(String n) {
        if (n != null) {
            name = n;
        }
    }
    public void printReport() {
        Logger.info("=== Bank Report ===");
        Logger.info("Name: {}", name);
        if ( clients != null && !clients.isEmpty() ) {
            Logger.info("Number of Clients: {}", clients.size());
            clients.forEach(Client::printReport);
        } else {
            Logger.info("The bank has no any clients.");
        }
    }

    public List<Client> getClients() {
        return clients;
    }
    public Client addClient(Client c) {
        if (c != null) {
            if (clients == null) {
                clients = new ArrayList<Client>();
            }
            clients.add(c);
        } else {
            Logger.info("Can not add empty client");
        }
        return c;
    }
}
