package comp3350.group6.promise.tests.business;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.business.AccessService;
import comp3350.group6.promise.business.AccountService;
import comp3350.group6.promise.business.AccountUserService;
import comp3350.group6.promise.business.NotifService;
import comp3350.group6.promise.business.ProjectService;
import comp3350.group6.promise.objects.Account;
import comp3350.group6.promise.objects.AccountUser;
import comp3350.group6.promise.objects.Exceptions.DuplicateEmailException;
import comp3350.group6.promise.objects.Exceptions.DuplicateNotificationException;
import comp3350.group6.promise.objects.Notification;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.util.DBConnectorUtil;

public class NotifServiceTestIT {

    private NotifService notifService;
    private AccountService accountService;
    private AccountUserService accountUserService;
    private AccessService accessService;
    private ProjectService projectService;

    @Before
    public void setup() {
        System.out.println("Starting integration test for NotifService");
        DBConnectorUtil.initialLocalDB();
        notifService = new NotifService();
        accountService = new AccountService();
        accountUserService = new AccountUserService();
        accessService = new AccessService();
        projectService = new ProjectService();
    }

    @After
    public void clean(){
        DBConnectorUtil.cleanLocalDB();
    }

    @Test
    public void testInviteRecipient() throws Exception {

        //Register 2 accounts and have the first one invite the second
        System.out.println("Testing project invite [NotifService]");
        //String testMessage = "Successful invite should mean this notification is in the database";

        //First account
        String firstEmail = "lazerrazor@email.com";
        String firstName = "Louise";
        String firstPass = "password";
        String firstIntro = "Nothing to see here";

        //Second account
        String nextEmail = "summertime@email.com";
        String nextName = "Summer";
        String nextPass = "password2";
        String nextIntro = "It's summer time!";

        //Register the first account
        accountService.register( firstEmail, firstName, firstPass, firstIntro );

        //Register the second account
        accountService.register( nextEmail, nextName, nextPass, nextIntro );

        //The second account is now the current account. Have it creates a project
        Project thisProject = projectService.insertProject(  new Project( "Winter", "Winter is the worst" ) );
        int projectID = thisProject.getProjectID();

        //Now have the second account invite the first account to this project
        notifService.invite( firstEmail, projectID );

        //Get the Account associated with the firstEmail (Louise) because we need their ID
        //(they're the recipient and getNotifs returns the notifications of the receiver)
        Account recipientAccount = accountService.getAccountByEmail( firstEmail );

        //Get a list of Louise's notifications to ensure the notification was added
        ArrayList<Notification> louiseNotifs = notifService.getNotifs( recipientAccount.getUserID() );

        //If this operation was successful, the list shouldn't be null
        String testMessage = "Addition should be successful so this shouldn't be null";
        assertNotNull( louiseNotifs, testMessage );

        //Make sure that we only have 1 notification because that's all we added
        assertEquals( 1, louiseNotifs.size(), "Size of the list should be 1" );

        //We only have 1 notification so we can just get the first element of the Notification list
        Notification checkNotif = louiseNotifs.get( 0 );
        AccountUser senderDetails = accountUserService.getUserByAccountID( checkNotif.getSenderID() );
        AccountUser recipientDetails = accountUserService.getUserByAccountID( checkNotif.getRecipientID() );

        assertEquals( nextName, senderDetails.getUserName(), "Notification was sent by" );
        assertEquals( firstName, recipientDetails.getUserName(), "Notification was received by" );
        assertEquals( projectID, checkNotif.getProjectID() );

    }

    @Test
    public void testInviteSender() throws Exception {

        //Register 2 accounts and have the first one invite the second
        System.out.println("Testing project invite [NotifService]");
        //String testMessage = "Successful invite should mean this notification is in the database";

        //First account
        String firstEmail = "lazerrazor@email.com";
        String firstName = "Louise";
        String firstPass = "password";
        String firstIntro = "Nothing to see here";

        //Second account
        String nextEmail = "summertime@email.com";
        String nextName = "Summer";
        String nextPass = "password2";
        String nextIntro = "It's summer time!";

        //Register the first account
        accountService.register( firstEmail, firstName, firstPass, firstIntro );

        //Register the second account
        accountService.register( nextEmail, nextName, nextPass, nextIntro );

        //The second account is now the current account. Have it creates a project
        Project thisProject = projectService.insertProject(  new Project( "Winter", "Winter is the worst" ) );
        int projectID = thisProject.getProjectID();

        //Now have the second account invite the first account to this project
        notifService.invite( firstEmail, projectID );

        //Get the Account associated with the secondEmail (Summer).
        //We want to make sure getNotifs is only returning based on recipient
        Account senderAccount = accountService.getAccountByEmail( nextEmail );

        //Get a list of Summer's notifications to ensure the notification wasn't added
        ArrayList<Notification> summerNotifs = notifService.getNotifs( senderAccount.getUserID() );

        //This account doesn't have any invites so the list should be empty
        assertTrue( summerNotifs.isEmpty(), "This list should be empty" );

    }

    @Test
    public void testAccept() throws Exception {
        //accepting an invitation should add the user to the list that have access
        System.out.println("Testing accept() [NotifService]");
        String testMessage = "accepting an invite should mean this user is now in the access table";

        //Register two accounts and have one invite the other
        //First account
        String firstEmail = "lazerrazor@email.com";
        String firstName = "Louise";
        String firstPass = "password";
        String firstIntro = "Nothing to see here";

        //Second account
        String nextEmail = "summertime@email.com";
        String nextName = "Summer";
        String nextPass = "password2";
        String nextIntro = "It's summer time!";

        //Register the first account
        accountService.register( firstEmail, firstName, firstPass, firstIntro );

        //Register the second account
        accountService.register( nextEmail, nextName, nextPass, nextIntro );

        //Have the second account create a project
        Project thisProject = projectService.insertProject(  new Project( "Winter", "Winter is the worst" ) );
        int projectID = thisProject.getProjectID();

        //Now have the second account invite the first account to this project
        notifService.invite( firstEmail, projectID );

        //Get the notification that was added
        Account recipient = accountService.getAccountByEmail( firstEmail );
        ArrayList<Notification> recipientNotifs = notifService.getNotifs(recipient.getUserID() );
        assertFalse( recipientNotifs.isEmpty(), "Notification was added so this shouldn't be empty" );
        Notification acceptInvite = recipientNotifs.get( 0 );

        //Make sure the user doesn't have any accesses to start
        assertTrue( accessService.getUserAccess( recipient.getUserID() ).isEmpty() );

        //Now the first account accepts the project invite
        notifService.accept( acceptInvite );

        //Now the user does have access to a project so the list shouldn't be empty anymore
        assertFalse( accessService.getUserAccess( recipient.getUserID()).isEmpty() );

        //Make sure it was the specific project they were invited to
        assertNotNull( accessService.getAccessByIDs( recipient.getUserID(), projectID ),
                "this shouldn't be null because the user accepted the invite");

    }

    @Test
    public void testAcceptRemoval() throws Exception {
        //once the user has accepted the invite, it should be removed from the notification database
        System.out.println("Testing accept() [NotifService]");
        String testMessage = "accepting an invite should mean this user is now in the access table";

        //Register two accounts and have one invite the other
        //First account
        String firstEmail = "lazerrazor@email.com";
        String firstName = "Louise";
        String firstPass = "password";
        String firstIntro = "Nothing to see here";

        //Second account
        String nextEmail = "summertime@email.com";
        String nextName = "Summer";
        String nextPass = "password2";
        String nextIntro = "It's summer time!";

        //Register the first account
        accountService.register( firstEmail, firstName, firstPass, firstIntro );

        //Register the second account
        accountService.register( nextEmail, nextName, nextPass, nextIntro );

        //Have the second account create a project
        Project thisProject = projectService.insertProject(  new Project( "Winter", "Winter is the worst" ) );
        int projectID = thisProject.getProjectID();

        //Now have the second account invite the first account to this project
        notifService.invite( firstEmail, projectID );

        //Get the notification that was added
        Account recipient = accountService.getAccountByEmail( firstEmail );
        ArrayList<Notification> recipientNotifs = notifService.getNotifs(recipient.getUserID() );

        //Make sure the Notifications list isn't empty to start
        assertFalse( recipientNotifs.isEmpty(), "Notification was added so this shouldn't be empty" );
        Notification acceptInvite = recipientNotifs.get( 0 );

        //Now the first account accepts the project invite
        notifService.accept( acceptInvite );

        //The Notification list should be empty now
        assertTrue( notifService.getNotifs( recipient.getUserID() ).isEmpty(),
                "We removed the single notification so this list should be empty now");
    }

    @Test
    public void testInviteAgain() throws Exception {

        //Register 2 accounts and have the first one invite the second
        System.out.println("Testing project invite [NotifService]");
        String testMessage = "Already invited so we should get a DuplicateNotificationException";

        //First account
        String firstEmail = "lazerrazor@email.com";
        String firstName = "Louise";
        String firstPass = "password";
        String firstIntro = "Nothing to see here";

        //Second account
        String nextEmail = "summertime@email.com";
        String nextName = "Summer";
        String nextPass = "password2";
        String nextIntro = "It's summer time!";

        //Register the first account
        accountService.register( firstEmail, firstName, firstPass, firstIntro );

        //Register the second account
        accountService.register( nextEmail, nextName, nextPass, nextIntro );

        //The second account is now the current account. Have it creates a project
        Project thisProject = projectService.insertProject(  new Project( "Winter", "Winter is the worst" ) );
        int projectID = thisProject.getProjectID();

        //Now have the second account invite the first account to this project
        notifService.invite( firstEmail, projectID );

        //Now have the second account invite the first account again
        assertThrows( testMessage, DuplicateNotificationException.class, () -> {
            notifService.invite( firstEmail, projectID );
        });
    }

    @Test
    public void testReject() throws Exception {
        //Register 2 accounts and have the first one invite the second
        System.out.println("Testing project invite [NotifService]");
        String testMessage = "Already invited so we should get a DuplicateNotificationException";

        //First account
        String firstEmail = "lazerrazor@email.com";
        String firstName = "Louise";
        String firstPass = "password";
        String firstIntro = "Nothing to see here";

        //Second account
        String nextEmail = "summertime@email.com";
        String nextName = "Summer";
        String nextPass = "password2";
        String nextIntro = "It's summer time!";

        //Register the first account
        accountService.register( firstEmail, firstName, firstPass, firstIntro );

        //Register the second account
        accountService.register( nextEmail, nextName, nextPass, nextIntro );

        //The second account is now the current account. Have it create a project
        Project thisProject = projectService.insertProject(  new Project( "Winter", "Winter is the worst" ) );
        int projectID = thisProject.getProjectID();

        //Now have the second account invite the first account to this project
        notifService.invite( firstEmail, projectID );

        //Get the notification that was added
        Account recipient = accountService.getAccountByEmail( firstEmail );
        ArrayList<Notification> recipientNotifs = notifService.getNotifs(recipient.getUserID() );

        //Make sure the Notifications list isn't empty to start
        assertFalse( recipientNotifs.isEmpty(), "Notification was added so this shouldn't be empty" );
        Notification rejectInvite = recipientNotifs.get( 0 );

        //Now the first account rejects the project invite
        notifService.reject( rejectInvite );

        //Make sure the user doesn't have any accesses to start
        assertTrue( accessService.getUserAccess( recipient.getUserID() ).isEmpty() );

        //The Notification list should be empty now
        assertTrue( notifService.getNotifs( recipient.getUserID() ).isEmpty(),
                "We removed the single notification so this list should be empty now");

        //Make sure the user still doesn't have accesses
        assertTrue( accessService.getUserAccess( recipient.getUserID() ).isEmpty() );

    }

}
