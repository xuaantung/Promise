package comp3350.group6.promise.objects;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

import comp3350.group6.promise.objects.enumClasses.TaskType;
import lombok.Data;

@Data
public class Task {
    private int taskId;
    private String title;
    private String description;
    private int priority;
    private int statusNum;
    private int projectId;
    private Timestamp createdTime;
    private Timestamp estimatedEndTime;
    private Timestamp deadline;
    private TaskType type;
    private static int count = 0;


    public Task(int taskId) {
        this.taskId = taskId;
        this.title = "";
        this.description = "";
        this.priority = 1;
        this.statusNum = 1;
        this.projectId = 100;
        this.createdTime = new Timestamp(System.currentTimeMillis());
        this.estimatedEndTime = null;
        this.deadline = null;
        this.type = TaskType.IP;
        count++;
    }

    /*
     *      - used by DB that generate from database
     */
    public Task(int taskId, String title, String description, int priority, int statusNum, int projectId, Timestamp createdTime, Timestamp estimatedEndTime, Timestamp deadline, TaskType type) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.statusNum = statusNum;
        this.projectId = projectId;
        this.createdTime = createdTime;
        this.estimatedEndTime = estimatedEndTime;
        this.deadline = deadline;
        this.type = type;
        count++;
    }

    /*
     *   - used by app when creating a task, doesn't have id
     *
     */
    public Task(String title, String description, int priority, int statusNum, int projectId, Timestamp estimatedEndTime, Timestamp deadline) {
        this.taskId = count;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.statusNum = statusNum;
        this.projectId = projectId;
        this.createdTime = new Timestamp(System.currentTimeMillis());
        this.estimatedEndTime = estimatedEndTime;
        this.deadline = deadline;
        this.type = TaskType.IP;
        count++;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(int statusNum) {
        this.statusNum = statusNum;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Timestamp getEstimatedEndTime() {
        return estimatedEndTime;
    }

    public void setEstimatedEndTime(Timestamp estimatedEndTime) {
        this.estimatedEndTime = estimatedEndTime;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", statusNum=" + statusNum +
                ", projectId=" + projectId +
                ", createdTime=" + createdTime +
                ", estimatedEndTime=" + estimatedEndTime +
                ", deadline=" + deadline +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return getTaskId() == task.getTaskId();
    }
}