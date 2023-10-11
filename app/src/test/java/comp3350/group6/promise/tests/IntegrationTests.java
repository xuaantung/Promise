package comp3350.group6.promise.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.group6.promise.tests.business.AccessServiceTest;
import comp3350.group6.promise.tests.business.AccountServiceTest;
import comp3350.group6.promise.tests.business.AccountUserServiceTest;
import comp3350.group6.promise.tests.business.HandleServiceIT;
import comp3350.group6.promise.tests.business.NotifServiceTestIT;
import comp3350.group6.promise.tests.business.ProjectServiceIT;
import comp3350.group6.promise.tests.business.TaskServiceTest;
import comp3350.group6.promise.tests.business.UserServiceIT;
import comp3350.group6.promise.tests.objects.UserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserTest.class,
        AccessServiceTest.class,
        AccountServiceTest.class,
        TaskServiceTest.class,
        ProjectServiceIT.class,
        HandleServiceIT.class,
        AccountUserServiceTest.class,
        NotifServiceTestIT.class,
        UserServiceIT.class
})


public class IntegrationTests {

}
