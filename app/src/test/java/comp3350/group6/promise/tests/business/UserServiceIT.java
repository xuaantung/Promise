package comp3350.group6.promise.tests.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import comp3350.group6.promise.business.UserService;
import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.util.DBConnectorUtil;

public class UserServiceIT {

    private UserService userService;

    @Before
    public void setup() {
        DBConnectorUtil.initialLocalDB();
        userService = UserService.getInstance();
    }

    @After
    public void clean() {
        DBConnectorUtil.cleanLocalDB();
    }

    @Test
    public void testAddUser() throws Exception {
        int userId = userService.addUser("sunsiwen", "introduction");
        assertTrue(userId > 0);
    }

    @Test
    public void testUpdateUserByUserId() throws Exception {
        int i = userService.updateUserByUserId(1, "SUNSIWEN", "introduction");
        assertTrue(i > 0);
    }

    @Test
    public void testGetUserByUserId() throws Exception {
        User user = userService.getUserByUserId(1);
        assertNotNull(user);
    }
}
