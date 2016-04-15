package com.acme.bankapp;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void checkAccountTypeNameCHECKING() {
        assertEquals("CHECKING", AccountTypes.CHECKING.toString());
    }

    @Test
    public void checkAccountTypeNameSAVING() {
        assertEquals("SAVING", AccountTypes.SAVING.toString());
    }

    @Test
    public void createCheckingAccount() {
        Account account;
        account = new CheckingAccount();
        assertEquals(AccountTypes.CHECKING, account.getType());
    }

    @Test
    public void createSavingAccount() {
        Account account;
        account = new SavingAccount();
        assertEquals(AccountTypes.SAVING, account.getType());
    }

    @Test
    public void checkCheckingAccountOverdraft() {
        final float overdraft = 123.0001f;
        CheckingAccount account;

        account = new CheckingAccount();
        account.setOverdraft(overdraft);
        assertEquals(overdraft, account.getOverdraft(), 0.00001);
    }

    @Test
    public void checkAccountId() {
        final long upper_limit = 99999999999999999l;
        final long lower_limit = 10000000000000000l;
        Account a;
        a = new SavingAccount();
        assertTrue("Saving Account id out of range",
                a.getAccountId() <= upper_limit
             && a.getAccountId() >= lower_limit);
        a = new CheckingAccount();
        assertTrue("Checking Account id out of range",
                a.getAccountId() <= upper_limit
             && a.getAccountId() >= lower_limit);
    }

    @Test
    public void enumerateAccountTypes() {
       for(AccountTypes account: AccountTypes.values()){
           System.out.println("Account Type: " + account);
       }
    }
}
