package comp3350.group6.promise.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import comp3350.group6.promise.objects.Notification;
import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.objects.enumClasses.NotifType;

public class UserTest {
    @Test
    public void testUser() {

        System.out.println("\nStarting testUser");

        final User user = new User(1, "sunsiwen", "do something");
        assertNotNull(user);
        assertEquals(1, user.getUserID());
        assertEquals("sunsiwen", user.getName());
        assertEquals("do something", user.getIntro());

        System.out.println("Finished testUser");
    }
}
