package comp3350.group6.promise.persistence;

import java.util.List;

import comp3350.group6.promise.objects.Account;
import comp3350.group6.promise.objects.AccountUser;

public interface AccountUserDao {

    AccountUser getUserByAccount( Account account );

    List<AccountUser> search(String searchTerm, int limit);

}