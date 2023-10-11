package comp3350.group6.promise.persistence;

import java.util.List;

import comp3350.group6.promise.objects.AccountUser;
import comp3350.group6.promise.objects.Handle;

public interface HandleDao {
    List<Handle> getUserTask(int taskId);
    List<Handle> getTaskUser(int userId);
    List<AccountUser> getTaskAssignees(int taskId);
    Handle insertHandle(Handle handle);
}