package comp3350.group6.promise.business;


import java.util.List;

import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.objects.enumClasses.TaskType;
import comp3350.group6.promise.persistence.TaskDao;
import comp3350.group6.promise.persistence.hsqldb.TaskImp;
//import comp3350.group6.promise.persistence.stub.TaskImpNoDB;

public class TaskService {
    private List<Task> allTask;
    private TaskDao taskDao;
    private static TaskService instance;


    public TaskService() {
        allTask = null;
        taskDao = new TaskImp();
    }


    public TaskService(TaskDao db) {
        this();
        this.taskDao = db;
    }

    public Task getTask(int taskId) {
        return taskDao.getTask(taskId);
    }

    public List<Task> getTasksByProjectId(int projectId, int type) {
        return taskDao.getTasksByProjectId(projectId, type);
    }

    public int insertTask(Task newTask) {
        return taskDao.insertTask(newTask);
    }

    public Task deleteTask(Task task) {
        taskDao.deleteTask(task);
        return task;
    }

    public Task updateTask(Task task) {
        taskDao.updateTask(task);
        return task;
    }

    public List<Task> getAllTask() {
        allTask = taskDao.getTaskList();
        return allTask;
    }

    public Task logTask(int taskId) {
        Task task = getTask(taskId);
        switch (task.getType()) {
            case IP:
                task.setType(TaskType.FINISHED);
                break;
            case FINISHED:
                task.setType(TaskType.IP);
                break;
        }
        taskDao.updateTask(task);
        return task;
    }

    public static TaskService getInstance() {
        if (TaskService.instance == null) {
            TaskService.instance = new TaskService();
        }
        return TaskService.instance;
    }

}


// TODO: sorting, compare
