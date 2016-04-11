package com.acme.bankapp;

import java.util.Calendar;
import java.util.GregorianCalendar;

abstract class AbstractAccount implements Report, Account {
    private long id;
    protected float balance;

    /**
     * id - is used to uniquely identify an account in the Bank.
     *      This method (year to millisecond) allows
     *      us to generate no more than 1000 unique account ids
     *      per second. It is the limit of my realization.
     */
    AbstractAccount() {
        GregorianCalendar c = new GregorianCalendar();
        id =
            (c.get(Calendar.YEAR)        * 100_00_00_00_00_000l) +
            ((c.get(Calendar.MONTH) + 1) * 100_00_00_00_000l) +
            (c.get(Calendar.DAY_OF_MONTH)* 100_00_00_000l) +
            (c.get(Calendar.HOUR_OF_DAY) * 100_00_000l) +
            (c.get(Calendar.MINUTE)      * 100_000l) +
            (c.get(Calendar.SECOND)      * 1000l) +
            c.get(Calendar.MILLISECOND);
    }

    public long getAccountId() {
        return id;
    }
}
