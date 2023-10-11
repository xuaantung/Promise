package comp3350.group6.promise.business;

import java.util.List;

import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Account;
import comp3350.group6.promise.objects.AccountUser;
import comp3350.group6.promise.persistence.AccountUserDao;
import comp3350.group6.promise.persistence.hsqldb.AccountUserImp;


/* This gives you the name of the user associated with an account */
public class AccountUserService {

    private final AccountUserDao accountUser = new AccountUserImp();
    private static AccountUserService instance;

    public AccountUser getUserByAccount(Account account) {

        return accountUser.getUserByAccount(account);

    }

    public AccountUser getUserByAccountID(int accountID) {

        Account account = Service.accounts.getAccountByID(accountID);
        return getUserByAccount(account);

    }

    public AccountUser getUserByEmail(String email) {

        Account account = Service.accounts.getAccountByEmail(email);
        return getUserByAccount(account);

    }

    public String getNameByEmail(String email) {

        AccountUser accountUser = getUserByEmail(email);
        return accountUser.getUserName();

    }

    public List<AccountUser> search(String searchTerm) {
        return accountUser.search(searchTerm, 3);
    }

    public static AccountUserService getInstance() {
        if (AccountUserService.instance == null) {
            AccountUserService.instance = new AccountUserService();
        }
        return AccountUserService.instance;
    }
}