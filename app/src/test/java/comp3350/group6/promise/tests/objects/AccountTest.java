package comp3350.group6.promise.tests.objects;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import comp3350.group6.promise.objects.Account;

import static org.junit.Assert.*;


public class AccountTest {
    @Test
    public void testAccount1() {

        System.out.println("\nStarting testAccount");

        final Account account = new Account("sunsiwen@promise.com", "123456", 1);
        assertNotNull(account);
        assertEquals("sunsiwen@promise.com", account.getEmail());
        assertEquals("123456", account.getPassword());
        assertEquals(1, account.getUserID());


        System.out.println("Finished testAccount");
    }

}
