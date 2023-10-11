package comp3350.group6.promise.objects;

import java.util.ArrayList;

public class FakeDB {
    public static int    generatedUserIDCount;
    public static ArrayList<User>       users;
    public static ArrayList<Account> accounts;
    public static ArrayList<Project> projects;
    public static ArrayList<Task>       tasks;
    public static ArrayList<Access>  accesses;

    public static void initialize() throws Exception {

        generatedUserIDCount = 0;
        users    = new ArrayList<User>();
        accounts = new ArrayList<Account>();
        projects = new ArrayList<Project>();
        accesses  = new ArrayList<Access>();
        tasks    = new ArrayList<Task>();
    }
}
