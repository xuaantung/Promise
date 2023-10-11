package comp3350.group6.promise.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import comp3350.group6.promise.objects.Notification;
import comp3350.group6.promise.objects.enumClasses.NotifType;

public class NotifTest {
    @Test
    public void testNotification() {

        System.out.println("\nStarting testNotification");

        final Notification notification = new Notification(1, 2, 3, NotifType.INVITE);
        assertNotNull(notification);
        assertEquals(1, notification.getSenderID());
        assertEquals(2, notification.getProjectID());
        assertEquals(3, notification.getRecipientID());
        assertEquals(NotifType.INVITE, notification.getType());


        System.out.println("Finished testNotification");
    }
}
