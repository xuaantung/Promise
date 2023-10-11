package comp3350.group6.promise.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.group6.promise.application.CurrentSession;
import comp3350.group6.promise.business.AccountService;
import comp3350.group6.promise.objects.Account;
import comp3350.group6.promise.objects.Exceptions.DuplicateEmailException;
import comp3350.group6.promise.objects.Exceptions.EmptyEmailException;
import comp3350.group6.promise.objects.Exceptions.EmptyPasswordException;
import comp3350.group6.promise.objects.Exceptions.LoginErrorException;
import comp3350.group6.promise.util.DBConnectorUtil;

public class AccountServiceTest {

    private AccountService accountService;

    /* Each time the fake DB get reinitialized */
    @Before
    public void setup() {
        System.out.println("Starting AccountServiceTest...");
        DBConnectorUtil.initialLocalDB();
        accountService = new AccountService();
    }

    @After
    public void clean(){
        DBConnectorUtil.cleanLocalDB();
    }

    @Test
    public void testAccountExists() throws Exception {

        System.out.println( "Testing that accountExists returns true [AccountService]" );
        String testMessage = "This email is registered so the account should exist";

        //First account
        String email1 = "wharfhorse@app.com";
        String name1 = "Calvin";
        String pass1 = "password1";
        String intro1 = "What";
        //Second account
        String email2 = "lazerrazor@app.com";
        String name2 = "Louise";
        String pass2 = "password2";
        String intro2 = "Nothing to see hereeee";
        //Third account
        String email3 = "summertime@email.com";
        String name3 = "Summer";
        String pass3 = "password3";
        String intro3 = "It's summer time!";

        //Register the first account
        accountService.register( email1, name1, pass1, intro1 );

        //Register the second account
        accountService.register( email2, name2, pass2, intro2 );

        //Register the third account
        accountService.register( email3, name3, pass3, intro3 );


        assertTrue( testMessage, accountService.accountExists( email1 ) );
        assertTrue( testMessage, accountService.accountExists( email2 ) );
        assertTrue( testMessage, accountService.accountExists( email3 ) );

    }

    @Test
    public void testAccountDNEEmpty(){

        System.out.println("Testing that accountExists returns false in an empty database [AccountService]");
        String testMessage = "This should return false because there are no accounts in the database";
        assertFalse( testMessage, accountService.accountExists( "something@email.com" ) );

    }

    @Test
    public void testAccountDNE() throws Exception {

        System.out.println("Testing that accountExists returns false with no matching email [AccountService]");
        String testMessage = "This should return false because this email isn't in the database";

        String email = "wharfhorse@app.com";
        String name = "Calvin";
        String password = "password1";
        String intro = "What";

        //Register this account
        accountService.register( email, name, password, intro );

        //Make sure an email that's not in the database returns false
        assertFalse( testMessage, accountService.accountExists( "wharfhorse@email.com" ) );

    }

    @Test
    public void testRegister() throws Exception {

        System.out.println("Testing account registration [AccountService]\n");
        String email = "wharfhorse@app.com";
        String name = "Calvin";
        String password = "password1";
        String intro = "What";

        //Test that the account isn't in the database originally
        assertNull( accountService.getAccountByEmail( email ));
        //Register with this email address
        accountService.register( email, name, password, intro );
        //Check that the account now exists in this database
        assertNotNull("This email should be in the database now", accountService.getAccountByEmail( email ) );

    }

    @Test
    public void testRegisterDuplicate() throws Exception {

        System.out.println("Testing account registration with an email that already exists\n");
        String testMessage = "This should throw a DuplicateEmailException.";

        //Duplicate email
        String email = "wharfhorse@app.com";

        //First registration
        String name = "Calvin";
        String password = "password1";
        String intro = "What";

        //Second registration
        String name2 = "Felix";
        String password2 = "password2";
        String intro2 = "What2";

        //Register with this email address
        accountService.register( email, name, password, intro );

        //Try to register with this email address again
        assertThrows( testMessage, DuplicateEmailException.class, () -> {
            accountService.register( email, name2, password2, intro2 );
        });

    }

    @Test
    public void testIncorrectLogin(){

        System.out.println("Testing login with credentials that don't exist [AccountService]");
        String testMessage = "This should throw a LoginErrorException";
        String email = "testLogin@email.com";
        String password = "password";

        //Try to register with this email and password
        assertThrows( testMessage, LoginErrorException.class, () -> {
            accountService.login( email, password );
        });

    }

    @Test
    public void testEmptyEmailRegister(){

        System.out.println("Testing account registration with an empty email [AccountService]");
        String testMessage = "This should throw an EmptyEmailException";
        String email = "";
        String name = "Dana";
        String password = "password";
        String intro = "Something";
        assertThrows( testMessage, EmptyEmailException.class, () -> {
            accountService.register( email, name, password, intro );
        });

    }

    @Test
    public void testEmptyPasswordRegister(){
        System.out.println("Testing account registration with an empty password [AccountService]");
        String testMessage = "This should throw an EmptyEmailException";
        String email = "something@something.com";
        String name = "Dana";
        String password = "";
        String intro = "Something";
        assertThrows( testMessage, EmptyPasswordException.class, () -> {
            accountService.register( email, name, password, intro );
        });
    }

    @Test
    public void testEmptyEmailLogin(){

        System.out.println("Testing login with an empty email [AccountService]");
        String testMessage = "This should throw an EmptyEmailException";
        String email = "";
        String password = "password";

        assertThrows( testMessage, EmptyEmailException.class, () -> {
           accountService.login( email, password );
        });

    }

    @Test
    public void testEmptyPasswordLogin(){

        System.out.println("Testing login with an empty password [AccountService]");
        String testMessage = "This should throw an EmptyPasswordException";
        String email = "something@email.com";
        String password = "";

        assertThrows( testMessage, EmptyPasswordException.class, () -> {
           accountService.login( email, password );
        });
    }

    @Test
    public void testCurrentUserRegister() throws Exception {

        System.out.println("Testing that account registration sets the current account [AccountService]");
        Account checkAccount;
        String email = "lazerrazor@app.com";
        String name = "Louise";
        String password = "password2";
        String intro = "Nothing to see here";
        String testMessage = "The should be true because the accounts should be equal";

        //Register with these details
        accountService.register( email, name, password, intro );

        //Get the account that was created and see if it's the current account
        checkAccount = accountService.getAccountByEmail( email );
        assertEquals(testMessage, CurrentSession.getAccount(), checkAccount);

    }

    @Test
    public void testCurrentUserRegisterChange() throws Exception {

        //Register twice in a row and make sure the current account is the second one
        System.out.println("Testing subsequent account registrations on current account [AccountService]");
        String testMessage = "The current account should match the second one registered";
        Account checkAccount;
        //First account
        String email1 = "lazerrazor@app.com";
        String name1 = "Louise";
        String pass1 = "password2";
        String intro1 = "Nothing to see here";
        //Second account
        String email2 = "summertime@email.com";
        String name2 = "Summer";
        String pass2 = "password3";
        String intro2 = "It's summer time!";

        //Register the first set of credentials
        accountService.register( email1, name1, pass1, intro1 );

        //Register the second set of credentials
        accountService.register( email2, name2, pass2, intro2 );

        //The current account should be the second one registered
        checkAccount = accountService.getAccountByEmail( email2 );
        assertEquals(testMessage, CurrentSession.getAccount(), checkAccount);

    }

    @Test
    public void testCurrentUserLogin() throws Exception {

        //This is also like testing login
        System.out.println("Testing that logging in sets the current account [AccountService]");
        Account checkAccount;
        String email = "lazerrazor@app.com";
        String name = "Louise";
        String password = "password2";
        String intro = "Nothing to see here";

        String throwEmail = "dummyemail@app.com";
        String throwName = "Dummy";
        String throwPass = "password3";
        String throwIntro = "something";

        String testMessage = "The should be true. The accounts should be equal";

        //Register with these details
        accountService.register( email, name, password, intro );

        //Register some other account
        accountService.register( throwEmail, throwName, throwPass, throwIntro );

        //Now login with the first set of credentials
        accountService.login( email, password );

        //Get the account with first email and see if it's the current account
        checkAccount = accountService.getAccountByEmail( email );
        assertEquals(testMessage, CurrentSession.getAccount(), checkAccount);

    }

    @Test
    public void testCurrentUserLoginChange() throws Exception {

        System.out.println("Testing subsequent logins on the current user");
        String testMessage = "This should be true. CurrentSession.currentUser should match second login";

        Account checkAccount;
        //First account
        String email1 = "lazerrazor@app.com";
        String name1 = "Louise";
        String pass1 = "password2";
        String intro1 = "Nothing to see hereeee";
        //Second account
        String email2 = "summertime@email.com";
        String name2 = "Summer";
        String pass2 = "password3";
        String intro2 = "It's summer time!";
        //Third account
        String throwEmail = "dummyemail@app.com";
        String throwName = "Dummy";
        String throwPass = "password3";
        String throwIntro = "something";

        //Register the first set of credentials
        accountService.register( email1, name1, pass1, intro1 );

        //Register the second set of credentials
        accountService.register( email2, name2, pass2, intro2 );

        //Register the third set of credentials
        accountService.register( throwEmail, throwName, throwPass, throwIntro );

        //Login with the first set of credentials
        accountService.login( email1, pass1 );

        //Login with the second set of credentials
        accountService.login( email2, pass2 );

        //The current account should be the second one registered
        checkAccount = accountService.getAccountByEmail( email2 );
        assertEquals(testMessage, CurrentSession.getAccount(), checkAccount);

    }

}
