package comp3350.group6.promise.tests.business;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.business.TaskService;
import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.objects.Exceptions.PersistenceException;
import comp3350.group6.promise.objects.enumClasses.TaskType;
import comp3350.group6.promise.persistence.TaskDao;
import comp3350.group6.promise.persistence.stub.TaskImpNoDB;
import comp3350.group6.promise.util.DBConnectorUtil;

public class TaskServiceTest {

    private TaskService taskService;

    private TaskDao taskDao; // Mockito for new feature only


    @Test
    public void testLogTask() {
        System.out.println("\nStarting testLogTask");
        taskDao = mock(TaskDao.class);
        taskService = new TaskService(taskDao);

        Task original = new Task(1, null, null, -1, -1, -1, null, null, null, TaskType.IP);
        Task updated = new Task(1, "HI", null, -1, -1, -1, null, null, null, TaskType.FINISHED);

        when(taskDao.getTask(1)).thenReturn(original);
//        when(taskDao.updateTask(original)).thenReturn(updated); // not necessary since log only return from getTask()
        Task result = taskService.logTask(1);
        assertEquals(TaskType.FINISHED, result.getType());

        when(taskDao.getTask(1)).thenReturn(updated);
        result = taskService.logTask(1);
        assertEquals(TaskType.IP, result.getType());
        System.out.println("\nFinished testLogTask");
    }


    /* Each time get reinitialized */
    @Before
    public void setup() {
        System.out.println("Starting test for TaskService");
        taskService = new TaskService(new TaskImpNoDB());
    }

    @Test
    public void testGetTaskList() {
        System.out.println("\nStarting testGetTaskList");
        List<Task> taskList = taskService.getAllTask();
        int size = 5;

        assertEquals(size, taskList.size());

        System.out.println("Finished testGetTaskList");
    }

    @Test
    public void testGetTaskById() {
        System.out.println("\nStarting testGetTaskById");
        List<Task> taskList = taskService.getAllTask();

        assertEquals(5, taskList.size());

        Task expected = new Task(1);
        Task actual = taskService.getTask(1);

        assertEquals(expected, actual);

        System.out.println("Finished testGetTaskById");
    }

    @Test
    public void testInsertTask() {
        System.out.println("\nStarting testInsertTask");

        List<Task> taskList = taskService.getAllTask();
        int oldSize = taskList.size();
        int newSize = oldSize + 1;
        Task toInsert = new Task(100);

        taskService.insertTask(toInsert);
        assertEquals(newSize, taskService.getAllTask().size());

        taskService.insertTask(new Task(100));

        assertEquals(newSize, taskService.getAllTask().size());

        System.out.println("Finished testInsertTask");
    }

    @Test
    public void testUpdateTask() {
        System.out.println("\nStarting testUpdateTask");

        Task toUpdate = new Task(1, "updatedTask", "default", 0, 0, 0, null, null, null, TaskType.IP);
        taskService.updateTask(toUpdate);

        String newTitle = "updatedTask";
        Task actual = taskService.getTask(1);
        assertEquals(newTitle, actual.getTitle());

        System.out.println("Finished testUpdateTask");
    }

    @Test
    public void testDeleteTask() {
        System.out.println("\nStarting testDeleteTask");

        List<Task> taskList = taskService.getAllTask();
        int oldSize = taskList.size();
        int newSize = oldSize - 1;
        Task toDelete = new Task(1);

        taskService.deleteTask(toDelete);
        assertEquals(newSize, taskService.getAllTask().size());

        taskService.deleteTask(new Task(1)); // invalid delete
        assertEquals(newSize, taskService.getAllTask().size());

        System.out.println("Finished testDeleteTask");
    }


    @Test
    public void testInsert() {
        System.out.println("\nStarting testInsertTask");

        List<Task> taskList = taskService.getAllTask();
        int oldSize = taskList.size();
        int newSize = oldSize + 2;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Task test = new Task("name", "", 0, 0, 0, timestamp, timestamp);

        Task test2 = new Task("name", "", 0, 0, 0, timestamp, timestamp);
        taskService.insertTask(test);
        taskService.insertTask(test2);
        assertEquals(newSize, taskService.getAllTask().size());
        System.out.println("Finished testInsertTask");

    }

    @After
    public void tearDown() {
        System.out.println("Reset database");
        taskService = null;
    }
}
