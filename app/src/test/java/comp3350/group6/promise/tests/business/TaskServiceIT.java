package comp3350.group6.promise.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import comp3350.group6.promise.business.TaskService;
import comp3350.group6.promise.objects.Exceptions.PersistenceException;
import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.objects.enumClasses.TaskType;
import comp3350.group6.promise.util.DBConnectorUtil;

public class TaskServiceIT {


    private TaskService taskService;

    /* Each time get reinitialized */
    @Before
    public void setup() {
        System.out.println("Starting test for TaskService");
        DBConnectorUtil.initialLocalDB();
        taskService = TaskService.getInstance();
    }

    @Test
    public void testLogTask(){
        System.out.println("\nStarting testLogTask");
        taskService.logTask(1);
        Task result = taskService.getTask(1);
        assertEquals(TaskType.FINISHED, result.getType());

        taskService.logTask(1);
        result = taskService.getTask(1);
        assertEquals(TaskType.IP, result.getType());

        System.out.println("\nFinished testLogTask");
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
        Exception e = assertThrows(PersistenceException.class, () -> {
            taskService.getTask(10);
        });

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

        taskService.insertTask(new Task(100));// the db will auto increment PK

        assertEquals(newSize + 1, taskService.getAllTask().size());

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
        DBConnectorUtil.cleanLocalDB(); // clean local db

    }
}