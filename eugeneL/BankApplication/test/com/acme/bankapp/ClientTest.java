/**
 * Package: com.acme.bankapp
 * Project: BankApplication
 */
package com.acme.bankapp;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    final static private float delta = 0.001f;
    final static private float defaultOverdraft = 300f; // must be the same as in class Client
    final static private String defaultClientName = "Unnamed Client"; // must be the same as in class Client

    @Test
    public void createClientDefault() {
        final Client client;

        client = new Client();
        assertEquals("Client(): Client Name incorrect", defaultClientName, client.getName());
        assertEquals("Client(): initialOverdraft incorrect", defaultOverdraft, client.getInitialOverdraft(), delta);
    }

    @Test
    public void createClientWithNameAndOverdraft() {
        final String name = "Test Client Name";
        final float initialOverdraft = 100.34f;
        final Client client;

        client = new Client(name, initialOverdraft);
        assertEquals("Client(name, initialOverdraft): Client Name incorrect", name, client.getName());
        assertEquals("Client(name, initialOverdraft): initialOverdraft incorrect", initialOverdraft, client.getInitialOverdraft(), delta);
    }

     @Test
    public void createClientWithName() {
        final String name = "Test Client Name";
        final Client client;

        client = new Client(name);
        assertEquals("Client(name): Client Name incorrect", name, client.getName());
        assertEquals("Client(name): initialOverdraft incorrect", defaultOverdraft, client.getInitialOverdraft(), delta);
    }

     @Test
    public void createClientWithOverdraft() {
        final float initialOverdraft = 100.34f;
        final Client client;

        client = new Client(initialOverdraft);
        assertEquals("Client(initialOverdraft): Client Name incorrect", defaultClientName, client.getName());
        assertEquals("Client(initialOverdraft): initialOverdraft incorrect", initialOverdraft, client.getInitialOverdraft(), delta);
    }
}
