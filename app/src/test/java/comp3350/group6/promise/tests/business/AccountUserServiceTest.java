package comp3350.group6.promise.tests.business;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.group6.promise.business.AccountService;
import comp3350.group6.promise.business.AccountUserService;
import comp3350.group6.promise.objects.AccountUser;
import comp3350.group6.promise.objects.Exceptions.DuplicateEmailException;
import comp3350.group6.promise.util.DBConnectorUtil;

public class AccountUserServiceTest {

    private AccountUserService accountUserService;
    private AccountService accountService;

    @Before
    public void setUp() {

        System.out.println( "Starting AccountUserServiceTest...");
        DBConnectorUtil.initialLocalDB();
        accountUserService = new AccountUserService();
        accountService = new AccountService();
    }

    @After
    public void clean(){
        DBConnectorUtil.cleanLocalDB();
    }

    @Test
    public void testUserName() throws Exception {

        System.out.println( "Testing getUserByAccount returns the right name [AccountUserService]" );

        //First account
        String firstEmail = "summertime@email.com";
        String firstName = "Summer";
        String firstPass = "password";
        String firstIntro = "It's summer time!";

        //Second account
        String nextEmail = "lazerrazor@app.com";
        String nextName = "Louise";
        String nextPass = "password1";
        String nextIntro = "Nothing to see here";

        //Register the first account
        accountService.register( firstEmail, firstName, firstPass, firstIntro );

        //Register the second account
        accountService.register( nextEmail, nextName, nextPass, nextIntro );

        //Get the User object associated to the first account
        AccountUser firstUser = accountUserService.getUserByAccount( accountService.getAccountByEmail( firstEmail ) );
        assertEquals( firstName, firstUser.getUserName() );

        //Get the User object associated to the second account
        AccountUser nextUser = accountUserService.getUserByAccount( accountService.getAccountByEmail( nextEmail ) );
        assertEquals( nextName, nextUser.getUserName() );

    }

    @Test
    public void testUserNameDuplicate() throws Exception {

        System.out.println( "Testing that getUserByAccount only returns the user associated with " +
                "the registered account [AccountServiceTest]");

        String duplicateEmail = "summertime@email.com";
        //First account
        String firstName = "Summer";
        String firstPass = "password";
        String firstIntro = "It's summer time!";

        //Second account
        String nextEmail = "lazerrazor@app.com";
        String nextName = "Louise";
        String nextPass = "password1";
        String nextIntro = "Nothing to see here";

        //Third account duplicate
        String thirdName = "Elaina";
        String thirdPass = "password2";
        String thirdIntro = "This shouldn't replace first account";

        //Register the first account
        accountService.register( duplicateEmail, firstName, firstPass, firstIntro );

        //Register the second account
        accountService.register( nextEmail, nextName, nextPass, nextIntro );

        //Register the last account (won't work since it's a duplicate)
        try{
            accountService.register( duplicateEmail, thirdName, thirdPass, thirdIntro );
        }

        catch( DuplicateEmailException e ){

            //Get the User object associated to the first account
            AccountUser firstUser = accountUserService.getUserByAccount( accountService.getAccountByEmail( duplicateEmail ) );
            assertEquals( firstName, firstUser.getUserName() );

        }

    }

}