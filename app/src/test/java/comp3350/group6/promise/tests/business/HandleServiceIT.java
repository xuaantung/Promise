package comp3350.group6.promise.tests.business;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.business.HandleService;
import comp3350.group6.promise.objects.Handle;
import comp3350.group6.promise.util.DBConnectorUtil;


// This is Integration test class for Handle
public class HandleServiceIT {
    private HandleService handleService;

    @Before
    public void setUp() {
        System.out.println("Start Integration Test for HandleService");
        DBConnectorUtil.initialLocalDB();
        handleService = HandleService.getInstance();
        assertNotNull(this.handleService);
    }

    @Test
    public void testGetListOfUserTask() {
        System.out.println("\nStarting testGetListUserTask");
        List<Handle> listOfUserTask = handleService.getListOfUserTask(1);
        assertEquals(4, listOfUserTask.size());
        System.out.println("Finish testGetListOfUserTask");
    }


    @Test
    public void testGetListOfTaskUser() {
        System.out.println("\nStarting testGetListOfTaskUser");
        List<Handle> listOfTaskUser = handleService.getListOfTaskUser(1); // "How many tasks that User 1 handle"
        assertEquals(3, listOfTaskUser.size());
        System.out.println("Finish testGetListOfTaskUser");
    }

    @Test
    public void testInsertHandle() {
        System.out.println("\nStarting testInsertHandle");
        //CASE 1 for user
        List<Handle> listOfTaskUser1 = handleService.getListOfTaskUser(1); // tasks User 1 handle
        assertEquals(3, listOfTaskUser1.size());

        Handle handleU = new Handle(4, 1, new Timestamp(System.currentTimeMillis()));
        handleService.insertHandle(handleU);

        listOfTaskUser1 = handleService.getListOfTaskUser(1);
        assertEquals(4, listOfTaskUser1.size());

        //CASE 2 for task
        List<Handle> listOfUserTask1 = handleService.getListOfUserTask(1); // tasks User 1 handle
        assertEquals(4, listOfUserTask1.size());

        Handle handleT = new Handle(1, 5, new Timestamp(System.currentTimeMillis()));
        handleService.insertHandle(handleT);

        listOfUserTask1 = handleService.getListOfUserTask(1);
        assertEquals(5, listOfUserTask1.size());

    }

    @After
    public void tearDown() {
        System.out.println("Reset database");
        DBConnectorUtil.cleanLocalDB(); // clean local db
    }
}
