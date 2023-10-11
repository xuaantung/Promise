package comp3350.group6.promise.objects;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.Data;

@Data
public class Handle {
    private int taskId;
    private int userId;
    private Timestamp since;

    public Handle(int taskId, int userId, Timestamp since) {
        this.taskId = taskId;
        this.userId = userId;
        this.since = since;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getSince() {
        return since;
    }

    public void setSince(Timestamp since) {
        this.since = since;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Handle handle = (Handle) o;
        return  taskId == handle.taskId && userId == handle.userId && Objects.equals(since, handle.since);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, userId, since);
    }
}


