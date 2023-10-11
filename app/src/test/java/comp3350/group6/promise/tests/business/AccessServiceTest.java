package comp3350.group6.promise.tests.business;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.List;

import comp3350.group6.promise.business.AccessService;
import comp3350.group6.promise.business.ProjectService;
import comp3350.group6.promise.business.UserService;
import comp3350.group6.promise.objects.Access;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.util.DBConnectorUtil;

public class AccessServiceTest {

    private AccessService accessService;
    private ProjectService projectService;
    private UserService userService;

    private Project p1, p2;
    private int uid1, uid2;
    private Access acc1, acc2, acc3, acc4;

    @Before
    public void setup(){
        DBConnectorUtil.initialLocalDB();

        accessService = AccessService.getInstance();
        projectService = ProjectService.getInstance();
        userService = UserService.getInstance();

        try {
            p1 = projectService.insertProject(new Project("Project 1", "This is a test."));
            p2 = projectService.insertProject(new Project("Project 2", "This is a test."));

            uid1 = userService.addUser("user1", "Hello I'm user1");
            uid2 = userService.addUser("user2", "Hello I'm user2");

            acc1 = new Access(p1.getProjectID(), uid1);
            acc2 = new Access(p2.getProjectID(), uid1);
            acc3 = new Access(p1.getProjectID(), uid2);
            acc4 = new Access(p2.getProjectID(), uid2);

            accessService.insertAccess(acc1);
            accessService.insertAccess(acc2);
            accessService.insertAccess(acc3);
            accessService.insertAccess(acc4);

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e);
        }
    }

    @After
    public void clean(){
        DBConnectorUtil.cleanLocalDB();
    }

    @Test
    public void testGetProjectAccess() {
        System.out.println("Testing getProjectAccess");

        // check for accesses p1 has
        List<Access> accessList1 = accessService.getProjectAccess(p1.getProjectID());
        assertTrue(accessList1.contains(acc1));
        assertTrue(accessList1.contains(acc3));

        // check for accesses p2 has
        List<Access> accessList2 = accessService.getProjectAccess(p2.getProjectID());
        assertTrue(accessList2.contains(acc2));
        assertTrue(accessList2.contains(acc4));

        System.out.println("Passed getProjectAccess");
    }

    @Test
    public void testGetUsers() throws Exception{
        System.out.println("Testing getUsers");

        // check for user list for p1
        List<User> userList1 = accessService.getUsers(p1.getProjectID());
        assertTrue(userList1.contains(userService.getUserByUserId(uid1)));
        assertTrue(userList1.contains(userService.getUserByUserId(uid2)));

        // check for user list for p2
        List<User> userList2 = accessService.getUsers(p2.getProjectID());
        assertTrue(userList2.contains(userService.getUserByUserId(uid1)));
        assertTrue(userList2.contains(userService.getUserByUserId(uid2)));

        System.out.println("Passed getUsers");
    }

    @Test
    public void testGetUserAccess(){
        System.out.println("Testing getUserAccess");

        // check for accesses uid1 has
        List<Access> accessList1 = accessService.getUserAccess(uid1);
        assertTrue(accessList1.contains(acc1));
        assertTrue(accessList1.contains(acc2));

        // check for accesses uid2 has
        List<Access> accessList2 = accessService.getUserAccess(uid2);
        assertTrue(accessList2.contains(acc3));
        assertTrue(accessList2.contains(acc4));

        System.out.println("Passed getUserAccess");
    }

    @Test
    public void testGetProjects(){
        System.out.println("Testing getProjects");

        // check for project list for uid1
        List<Project> projectList1 = accessService.getProjects(uid1);
        assertTrue(projectList1.contains(p1));
        assertTrue(projectList1.contains(p2));

        List<Project> projectList2 = accessService.getProjects(uid2);
        assertTrue(projectList2.contains(p1));
        assertTrue(projectList2.contains(p2));

        System.out.println("Passed getProject");
    }

    @Test
    public void testGetAccessByIDs(){
        System.out.println("Testing getAccessByIDs");

        assertEquals(acc1, accessService.getAccessByIDs(uid1, p1.getProjectID())); // acc1 = uid1 & p1
        assertEquals(acc2, accessService.getAccessByIDs(uid1, p2.getProjectID())); // acc2 = uid1 & p2
        assertEquals(acc3, accessService.getAccessByIDs(uid2, p1.getProjectID())); // acc3 = uid2 & p1
        assertEquals(acc4, accessService.getAccessByIDs(uid2, p2.getProjectID())); // acc4 = uid2 & p2

        System.out.println("Passed getAccessByIDs");
    }

    @Test
    public void testUpdateAccess(){
        System.out.println("Testing updateAccess");

        Access target;

        // acc1 = uid1 & p1
        String role = acc1.getRole();
        assertTrue(role.equals("MEMBER"));

        acc1.setRole("MANAGER");
        accessService.updateAccess(acc1);

        target = accessService.getAccessByIDs(uid1, p1.getProjectID());
        assertTrue(target.getRole().equals("MANAGER"));

        System.out.println("Passed updateAccess");
    }
}
