package comp3350.group6.promise.objects;

import java.sql.Timestamp;
import java.util.Objects;

import lombok.Data;

@Data
public class Project {

    private static int count = 0;
    private int projectID;
    private String projectName;
    private String statement;
    private int statusNum;
    private Timestamp createdTime;
    private Timestamp estimatedEndTime;

    public Project(String projectName, String statement){
        this.projectName = projectName;
        this.projectID = count;
        if (statement.equals("")){
            this.statement = "project statement";
        }
        this.statement = statement;
        this.statusNum = 0; //TODO: [Future iterations] create project status
        
        Timestamp time = new Timestamp(System.currentTimeMillis());
        this.createdTime = time;
        this.estimatedEndTime = time; //TODO: [Future iterations] check estimated time

        count++;
    }

    public Project(int projectID, String projectName, String statement, int statusNum, Timestamp createdTime, Timestamp estimatedEndTime) {
        this.projectID = projectID;
        this.projectName = projectName;
        this.statement = statement;
        this.statusNum = statusNum;
        this.createdTime = createdTime;
        this.estimatedEndTime = estimatedEndTime;
        count++;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", projectName='" + projectName + '\'' +
                ", statement='" + statement + '\'' +
                ", statusNum=" + statusNum +
                ", createdTime=" + createdTime +
                ", estimatedEndTime=" + estimatedEndTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        return projectID == project.projectID &&
               statusNum == project.statusNum &&
               projectName.equals(project.projectName) &&
               Objects.equals(statement, project.statement) &&
               createdTime.equals(project.createdTime) &&
               Objects.equals(estimatedEndTime, project.estimatedEndTime);
    }

    public int getProjectID(){
        return this.projectID;
    }

    public String getProjectName(){
        return this.projectName;
    }

    public String getStatement(){
        return this.statement;
    }

    public int getStatusNum(){
        return this.statusNum;
    }

    public Timestamp getCreatedTime(){
        return this.createdTime;
    }

    public Timestamp getEstimatedEndTime(){
        return this.estimatedEndTime;
    }

    public void setProjectID(int projectID){
        this.projectID = projectID;
    }

    public void setProjectName(String newName){
        this.projectName = newName;
    }

    public void setStatement(String statement){
        this.statement = statement;
    }

    public void setStatusNum(int status){
        this.statusNum = status;
    }

    public void setEstimatedEndTime(Timestamp time){
        this.estimatedEndTime = time;
    } 
}