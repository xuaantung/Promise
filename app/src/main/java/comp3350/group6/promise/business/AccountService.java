package comp3350.group6.promise.business;
import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Account;
import comp3350.group6.promise.objects.Exceptions.DuplicateEmailException;
import comp3350.group6.promise.objects.Exceptions.EmptyEmailException;
import comp3350.group6.promise.objects.Exceptions.EmptyPasswordException;
import comp3350.group6.promise.objects.Exceptions.LoginErrorException;
import comp3350.group6.promise.persistence.AccountDao;
import comp3350.group6.promise.persistence.hsqldb.AccountImp;

public class AccountService {

    private static final AccountDao accountDao = new AccountImp();
    private static AccountService instance;

    //changed this method to have a void return statement and just use Exception handling - ask if OK
    private void createAccount(String email, String password, String name, String introduction) throws Exception {

        int userID = Service.users.addUser(name, introduction);

        try {
            accountDao.createAccount(email, password, userID);
        }
        catch (DuplicateEmailException e) {
            throw new DuplicateEmailException("An account with this email already exists");
        }


    }

    public boolean changePassword(int userID, String oldPassword, String newPassword) throws Exception {

        return 1 == accountDao.changePassword(userID, oldPassword, newPassword);

    }

    public boolean accountExists(String email) {

        return accountDao.accountExists(email);

    }

    private boolean passwordsMatch(String email, String password) {

        return accountDao.passwordsMatch(email, password);

    }

    public Account getAccountByEmail(String email) {

        return accountDao.getAccountByEmail(email);

    }

    public Account getAccountByID(int userID) {

        return accountDao.getAccountByID(userID);

    }

    private void setCurrentAccount(String email, String password) throws LoginErrorException {

        //for us to set the current user, the account has to exist and the password must match
        if (accountExists(email) && passwordsMatch(email, password))
            CurrentSession.setAccount(accountDao.getAccountByEmail(email));

            //whether the account doesn't exist or the passwords don't match, we send the same error
        else
            throw new LoginErrorException("Wrong password/email combination");


    }

    public void register(String email, String name, String password, String intro) throws Exception {

        if (email.equals(""))
            throw new EmptyEmailException("Please enter an email address.");

        if (password.equals(""))
            throw new EmptyPasswordException("Please enter a password.");

        //If no Exceptions were thrown, create this account and set it as current user
        try {

            createAccount(email, password, name, intro);
            setCurrentAccount(email, password);

        }
        catch (DuplicateEmailException e) {
            throw new DuplicateEmailException(e.getMessage());
        }
        catch (LoginErrorException e) {
            throw new LoginErrorException("Wrong password/email combination");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void login(String email, String password) throws Exception {

        if (email.equals(""))
            throw new EmptyEmailException("Please enter an email address");

        if (password.equals(""))
            throw new EmptyPasswordException("Please enter a password");

        //If no Exceptions are thrown, set this as the current user
        try {
            setCurrentAccount(email, password);
        } catch (LoginErrorException e) {
            throw new LoginErrorException("Wrong password/email combination");
        }

    }

    public void logout() {
        CurrentSession.setAccount(null);
    }

    public static AccountService getInstance() {
        if (AccountService.instance == null) {
            AccountService.instance = new AccountService();
        }
        return AccountService.instance;
    }
}
