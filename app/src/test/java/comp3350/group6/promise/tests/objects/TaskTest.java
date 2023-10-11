package comp3350.group6.promise.tests.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.objects.enumClasses.TaskType;

public class TaskTest {
    Task task;
    Timestamp cTime;
    Timestamp eTime;
    Timestamp dTime;

    @Before
    public void setup() {
        System.out.println("Starting test for Task object");
        cTime = new Timestamp(System.currentTimeMillis());
        eTime = new Timestamp(System.currentTimeMillis());
        dTime = new Timestamp(System.currentTimeMillis());
        task = new Task(1, "myTask", "default", 0, 0, 0, cTime, eTime, dTime, TaskType.IP);
    }


    @Test
    public void testCreateTask() {
        System.out.println("\nStarting testCreateTask");

        assertEquals(1, task.getTaskId());
        assertEquals("myTask", task.getTitle());
        assertEquals("default", task.getDescription());
        assertEquals(0, task.getPriority());
        assertEquals(0, task.getStatusNum());
        assertEquals(0, task.getProjectId());
        assertTrue(cTime.equals(task.getCreatedTime()));
        assertTrue(eTime.equals(task.getEstimatedEndTime()));
        assertTrue(dTime.equals(task.getDeadline()));

        System.out.println("Finished testCreateTask");
    }

    @Test
    public void testSetTitle() {
        System.out.println("\nStarting testSetTitle");
        String nTitle = "newTitle";
        String oTitle = task.getTitle();
        task.setTitle("newTitle");

        assertNotEquals(oTitle, task.getTitle());
        assertEquals(nTitle, task.getTitle());

        System.out.println("Finished testSetTitle");
    }

    @Test
    public void testSetDescription() {
        System.out.println("\nStarting testSetDescription");
        String nDes = "newDes";
        String oDes = task.getDescription();
        task.setDescription("newDes");

        assertNotEquals(oDes, task.getDescription());
        assertEquals(nDes, task.getDescription());

        System.out.println("Finished testSetDescription");
    }

    @Test
    public void testSetPriority() {
        System.out.println("\nStarting testSetPriority");
        int oP = task.getPriority();
        int nP = 3;
        task.setPriority(3);

        assertNotEquals(oP, task.getPriority());
        assertEquals(nP, task.getPriority());

        System.out.println("Finished testSetPriority");
    }

    @Test
    public void testSetStatusNum() {
        System.out.println("\nStarting testSetStatusNum");
        int oS = task.getStatusNum();
        int nS = 3;
        task.setStatusNum(3);

        assertNotEquals(oS, task.getStatusNum());
        assertEquals(nS, task.getStatusNum());

        System.out.println("Finished testSetStatusNum");
    }


}
