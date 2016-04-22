/**
 * Package: com.acme.bankapp
 * Project: BankApplication
 */
package com.acme.bankapp;

import java.util.UUID;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class ClientTest {
    private static final float  delta = 0.001f;
    private static final float  defaultOverdraft = 300f; // must be the same as in class Client
    private static final String testClientName = "Test JUnit Client";
    private static final float  testOverdraft = 100.34f;
    private static final UUID   testId = UUID.fromString("12345678-abab-fdfe-9090-9876543210af");
    private static final UUID   nullId = null;
    private static final String nullName = null;
    private static final float  wrongOverdraft = -1;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // Constructor Client(String clientName)
    @Test
    public void createClientWithName() {
        final String functionId = "Client(testClientName)";
        final Client client;

        client = new Client(testClientName);
        assertNotNull(functionId + "Client Id should not be null", client.getId());
        assertEquals(functionId + "Client Name incorrect", testClientName, client.getName());
        assertEquals(functionId + "initialOverdraft incorrect", defaultOverdraft,
            client.getInitialOverdraft(), delta);
    }
    @Test
    public void createClientWithNullName() {
        final String functionId = "Client(nullName)";
        final Client client;

        client = new Client(nullName);
        assertNotNull(functionId + "Client Id should not be null", client.getId());
        assertNull(functionId + "Client Name", client.getName());
        assertEquals(functionId + "initialOverdraft incorrect", defaultOverdraft,
            client.getInitialOverdraft(), delta);
    }

    /**
     * Constructor Client(String clientName, float clientInitialOverdraft) throws Exception
     *  Variants:
     * {not null, correct}, {not null, wrong}, {null, correct}, {null, wrong}
     */
    @Test
    public void createClientWithNameAndOverdraft() throws Exception {
        final String functionId = "Client(testClientName, testOverdraft): ";
        final Client client;

        client = new Client(testClientName, testOverdraft);
        assertNotNull(functionId + "Client Id should not be null", client.getId());
        assertEquals(functionId + "Client Name incorrect",
            testClientName, client.getName());
        assertEquals(functionId + "initialOverdraft incorrect",
            testOverdraft, client.getInitialOverdraft(), delta);
    }
    @Test
    public void createClientWithNameAndWrongOverdraft() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Create new client: Wrong Initial Overdraft: " + wrongOverdraft);
        new Client(testClientName, wrongOverdraft);
    }
    @Test
    public void createClientWithNullNameAndOverdraft() throws Exception {
        final String functionId = "Client(nullName, testOverdraft): ";
        final Client client;

        client = new Client(nullName, testOverdraft);
        assertNotNull(functionId + "Client Id should not be null", client.getId());
        assertNull(functionId + "Client Name", client.getName());
        assertEquals(functionId + "initialOverdraft incorrect", testOverdraft,
            client.getInitialOverdraft(), delta);
    }
    @Test
    public void createClientWithNullNameAndWrongOverdraft() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Create new client: Wrong Initial Overdraft: " + wrongOverdraft);
        new Client(nullName, wrongOverdraft);
    }

    /**
     * Constructor public Client(UUID clientId, String clientName)
     *  Variants:
     *  {id, name}, {id, null name}, {null id, name}, {null id, null name}
     */
    @Test
    public void createClientWithIdAndName() {
        final String functionId = "Client(testId, testClientName): ";
        final Client client;

        client = new Client(testId, testClientName);
        assertEquals(functionId + "Wrong Id", testId, client.getId());
        assertEquals(functionId + "Client Name incorrect",
            testClientName, client.getName());
        assertEquals(functionId + "initialOverdraft incorrect",
            defaultOverdraft, client.getInitialOverdraft(), delta);
    }
    @Test
    public void createClientWithIdAndNullName () {
        final String functionId = "Client(testId, nullName): ";
        final Client client;

        client = new Client(testId, nullName);
        assertEquals(functionId + "Wrong Id", testId, client.getId());
        assertNull(functionId + "Client Name", client.getName());
        assertEquals(functionId + "initialOverdraft incorrect",
            defaultOverdraft, client.getInitialOverdraft(), delta);
    }
    @Test
    public void createClientWithNullIdAndName() {
        final String functionId = "Client(nullId, testClientName): ";
        final Client client;

        client = new Client(nullId, testClientName);
        assertNotNull(functionId + "Client Id should not be null", client.getId());
        assertEquals(functionId + "Client Name incorrect",
            testClientName, client.getName());
        assertEquals(functionId + "initialOverdraft incorrect",
            defaultOverdraft, client.getInitialOverdraft(), delta);
    }
    @Test
    public void createClientWithNullIdAndNullName() {
        final String functionId = "Client(nullId, nullName): ";
        final Client client;

        client = new Client(nullId, nullName);
        assertNotNull(functionId + "Client Id should not be null", client.getId());
        assertNull(functionId + "Client Name", client.getName());
        assertEquals(functionId + "initialOverdraft incorrect",
            defaultOverdraft, client.getInitialOverdraft(), delta);
    }

    /**
     * Constructor public Client(UUID clientId, String clientName, float clientInitialOverdraft) throws Exception {
     *  Variants:
     *  {id, name, correct}, {id, name, wrong},
     *  {id, null name, correct}, {id, null name, wrong},
     *  {null id, name, correct}, {null id, name, wrong},
     *  {null id, null name, correct}, {null id, null name, wrong}
     */
    @Ignore("Not implemented yet")
    @Test public void createClientIdNameOverdraft() { }
    @Ignore("Not implemented yet")
    @Test public void createClientIdNameWrongOverdraft() { }
    @Ignore("Not implemented yet")
    @Test public void createClientIdNullNameOverdraft() { }
    @Ignore("Not implemented yet")
    @Test public void createClientIdNullNameWrongOverdraft() { }
    @Ignore("Not implemented yet")
    @Test public void createClientNullIdNameOverdraft() { }
    @Ignore("Not implemented yet")
    @Test public void createClientNullIdNameWrongOverdraft() { }
    @Ignore("Not implemented yet")
    @Test public void createClientNullIdNullNameOverdraft() { }
    @Ignore("Not implemented yet")
    @Test public void createClientNullIdNullNameWrongOverdraft() { }
}
