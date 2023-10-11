package comp3350.group6.promise.application;

import comp3350.group6.promise.business.AccessService;
import comp3350.group6.promise.business.AccountService;
import comp3350.group6.promise.business.AccountUserService;
import comp3350.group6.promise.business.HandleService;
import comp3350.group6.promise.business.NotifService;
import comp3350.group6.promise.business.ProjectService;
import comp3350.group6.promise.business.TaskService;
import comp3350.group6.promise.business.UserService;

public class Service {

    public static UserService users = UserService.getInstance();
    public static AccountService accounts = AccountService.getInstance();
    public static ProjectService projects = ProjectService.getInstance();
    public static TaskService tasks = TaskService.getInstance();
    public static AccessService accesses = AccessService.getInstance();
    public static HandleService handles = HandleService.getInstance();
    public static AccountUserService accountUser = AccountUserService.getInstance();
    public static NotifService notifications = NotifService.getInstance();

}
