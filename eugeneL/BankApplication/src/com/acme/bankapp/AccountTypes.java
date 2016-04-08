/**
 * Package: com.acme.bankapp
 * Project: BankApplication
 */
package com.acme.bankapp;

enum AccountTypes {
    CHECKING("Checking"),
    SAVING("Saving");

    private final String name;

    AccountTypes(String n) {
        name = n;
    }

    public String getAccountTypeName() {
        return name;
    }
}
