package comp3350.group6.promise.objects;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

import comp3350.group6.promise.objects.enumClasses.AccessRole;
import lombok.Data;

/**
 * @author Dani
 */
@Data
public class Access {

    private int projectId;
    private int userId;
    private String role;
    private Timestamp startTime;

    public Access(int projectId, int userId) {
        this.projectId = projectId;
        this.userId = userId;
        this.role = AccessRole.MEMBER.name();
        this.startTime = new Timestamp(System.currentTimeMillis());
    }

    public Access(int projectId, int userId, String role, Timestamp startTime) {
        this.projectId = projectId;
        this.userId = userId;
        this.role = role;
        this.startTime = startTime;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp time) { this.startTime = time; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Access access = (Access) o;
        return projectId == access.projectId && userId == access.userId && Objects.equals(role, access.role) && Objects.equals(startTime, access.startTime);
    }

}
