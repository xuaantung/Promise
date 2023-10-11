package comp3350.group6.promise.persistence;

import comp3350.group6.promise.objects.Account;
import comp3350.group6.promise.objects.Exceptions.DuplicateEmailException;

public interface AccountDao {

    void createAccount(String email, String password, int userId) throws DuplicateEmailException;

    int changePassword(int userId, String oldPassword, String newPassword);

    boolean accountExists(String email);

    Account getAccountByEmail(String email);

    Account getAccountByID( int userID );

    boolean passwordsMatch(String email, String password);

}
