package com.acme.bankapp;

import java.util.UUID;

abstract class AbstractAccount implements Report, Account {
    private final UUID id;
    protected float balance;

    AbstractAccount() { this(UUID.randomUUID()); }
    AbstractAccount(UUID accountId) { id = accountId; }

    public UUID getId() { return id; }
}
