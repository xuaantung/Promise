package comp3350.group6.promise.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comp3350.group6.promise.application.Service;
import comp3350.group6.promise.objects.Account;
import comp3350.group6.promise.objects.AccountUser;
import comp3350.group6.promise.objects.Exceptions.PersistenceException;
import comp3350.group6.promise.objects.Handle;
import comp3350.group6.promise.objects.User;
import comp3350.group6.promise.persistence.HandleDao;
import comp3350.group6.promise.persistence.hsqldb.HandleImp;
import comp3350.group6.promise.util.DBConnectorUtil;

/*
    This class is used to handle the relationship between User and Task
*/

public class HandleService {

    private final HandleDao handleDao;
    private static HandleService instance;

    public HandleService() {
        handleDao = new HandleImp();
    }

    public List<Handle> getListOfUserTask(int taskId) {
        return handleDao.getUserTask(taskId);
    } // either return empty list or list of users associated with this task

    public List<Handle> getListOfTaskUser(int userId) {
        return handleDao.getTaskUser(userId);
    } // either return empty list or list of tasks associated with this user

    public List<AccountUser> getTaskAssignees(int taskId) {
        return handleDao.getTaskAssignees(taskId);
    }

    public  Handle insertHandle(Handle handle) {
        handleDao.insertHandle(handle);
        return handle;
    }

    public static HandleService getInstance() {
        if(HandleService.instance == null) {
            HandleService.instance = new HandleService();
        }
        return HandleService.instance;
    }

}
