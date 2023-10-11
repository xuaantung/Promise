package comp3350.group6.promise.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.group6.promise.tests.business.ProjectServiceTest;
import comp3350.group6.promise.tests.business.UserServiceTest;
import comp3350.group6.promise.tests.objects.AccessTest;
import comp3350.group6.promise.tests.objects.AccountTest;
import comp3350.group6.promise.tests.objects.NotifTest;
import comp3350.group6.promise.tests.objects.ProjectTest;
import comp3350.group6.promise.tests.objects.TaskTest;
import comp3350.group6.promise.tests.objects.UserTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessTest.class,
        AccountTest.class,
        NotifTest.class,
        ProjectTest.class,
        TaskTest.class,
        UserTest.class,
        ProjectServiceTest.class,
        UserServiceTest.class
})

public class AllUnitTests {}
