package comp3350.group6.promise.persistence.stub;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comp3350.group6.promise.objects.Task;
import comp3350.group6.promise.persistence.TaskDao;

public class TaskImpNoDB implements TaskDao {

    ArrayList<Task> taskList;
    Task task1, task2, task3, task4, task5;
    Timestamp cTime;
    Timestamp eTime;
    Timestamp dTime;


    public TaskImpNoDB() {
        initDB();
    }

    private void initDB() {
        task1 = new Task(1);
        task2 = new Task(2);
        task3 = new Task(3);
        task4 = new Task(4);
        task5 = new Task(5);


        taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);
    }

    @Override
    public List<Task> getTaskList() {
        return this.taskList;
    }

    @Override
    public Task getTask(int taskId) {
        if (getIndex(taskId) >= 0)
            return taskList.get(getIndex(taskId));
        return null;
    }

    @Override
    public List<Task> getTasksByProjectId(int projectId, int value) {
        List<Task> projectTasks = new ArrayList<Task>();
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            if (task.getProjectId() == projectId)
                projectTasks.add(task);
        }
        return projectTasks;
    }

    @Override
    public int insertTask(Task t) {
        if (getIndex(t.getTaskId()) == -1) {
            taskList.add(t);
            return t.getTaskId();
        }
        return -1;
    }

    @Override
    public Task updateTask(Task t) {
        if (getIndex(t.getTaskId()) >= 0) {
            taskList.set(getIndex(t.getTaskId()), t);
            return t;
        }
        return null;
    }

    @Override
    public Task deleteTask(Task t) {
        if (getIndex(t.getTaskId()) >= 0)
            taskList.remove(getIndex(t.getTaskId()));
        return t;
    }

    private int getIndex(int taskId) {
        for (int i = 0; i < taskList.size(); i++) {
            int res = taskList.get(i).getTaskId();
            if (res == taskId)
                return i;
        }
        return -1;
    }


}
