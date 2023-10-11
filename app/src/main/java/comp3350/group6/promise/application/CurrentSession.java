package comp3350.group6.promise.application;

import comp3350.group6.promise.objects.Account;

public class CurrentSession {

    private static Account currentUser = null;

    public static Account getAccount() {
        return currentUser;
    }

    public static void setAccount(Account account) {
        currentUser = account;
    }

}
