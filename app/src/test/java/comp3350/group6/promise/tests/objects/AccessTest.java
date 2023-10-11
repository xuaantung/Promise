package comp3350.group6.promise.tests.objects;

import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Timestamp;

import comp3350.group6.promise.objects.Access;

public class AccessTest {

    @Test
    public void testAccessConstruct(){
        System.out.println("\nStarting testAccessConstruct");

        Timestamp time = new Timestamp(System.currentTimeMillis());
        Access access = new Access(1, 1, "Owner", time);

        assertNotNull(access);
        assertTrue(access.getProjectId() == 1);
        assertTrue(access.getUserId() == 1);
        assertTrue(access.getRole() == "Owner");
        assertTrue(time.equals(access.getStartTime()));

        System.out.println("\nFinished testAccessConstruct");
    }

    @Test
    public void testAccessSet(){
        System.out.println("\nStarting testAccessConstruct");

        Timestamp time = new Timestamp(System.currentTimeMillis());
        Access access = new Access(1, 1);

        access.setProjectId(2);
        access.setUserId(2);
        access.setRole("Tester");
        access.setStartTime(time);

        assertNotNull(access);
        assertTrue(access.getProjectId() == 2);
        assertTrue(access.getUserId() == 2);
        assertTrue(access.getRole() == "Tester");
        assertTrue(time.equals(access.getStartTime()));

        System.out.println("\nFinished testAccessConstruct");
    }
}
