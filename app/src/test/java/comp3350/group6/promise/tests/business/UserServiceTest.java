package comp3350.group6.promise.tests.business;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comp3350.group6.promise.business.UserService;
import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.persistence.UserDao;
import comp3350.group6.promise.persistence.stub.UserImpNoDB;

public class UserServiceTest {

    private UserService userService;
    private UserDao userDao;

    @Before
    public void setup(){
        userService = new UserService(new UserImpNoDB());
    }


    @Test
    public void testAddUser() throws Exception{
        int userId = userService.addUser("sunsiwen", "introduction");
        assertTrue(userId == 1);
    }

    @Test
    public void testUpdateUserByUserId( ) throws Exception{
        int i = userService.updateUserByUserId(0, "SUNSIWEN", "introduction");
        assertTrue(i > 0);
    }

    @Test
    public void testGetUserByUserId( ) throws Exception{
        User user = userService.getUserByUserId(0);
        assertNotNull(user);
    }
}
