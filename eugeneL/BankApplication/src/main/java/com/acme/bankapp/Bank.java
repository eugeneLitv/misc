package com.acme.bankapp;

/**
 * Created with IntelliJ IDEA 2016.1.
 * User: eugeneL
 * Date: 26/03/16
 * Time: 18:49
 */
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
        System.out.println("=== Bank Report ===");
        System.out.println("Name: " + name);
        if ( clients != null && !clients.isEmpty() ) {
            System.out.println("Number of Clients: " + clients.size());
            clients.forEach(Client::printReport);
        } else {
            System.out.println("The bank has no any clients.");
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
            System.out.println("Can not add empty client");
        }
        return c;
    }
}
