package com.acme.bankapp;

import java.util.UUID;

abstract class AbstractAccount implements Report, Account {
    private final UUID id;
    protected float balance;

    /**
     * id - is used to uniquely identify an account in the Bank.
     *      This method (year to millisecond) allows
     *      us to generate no more than 1000 unique account ids
     *      per second. It is the limit of my realization.
     */
    AbstractAccount() {
        this(UUID.randomUUID());
    }
    AbstractAccount(UUID accountId) {
        id = accountId;
    }

    public UUID getId() { return id; }
}
