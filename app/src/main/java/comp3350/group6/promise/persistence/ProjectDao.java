package comp3350.group6.promise.persistence;

import java.util.List;

import comp3350.group6.promise.objects.Project;

public interface ProjectDao {

    //get a list of all projects in the database
    List<Project> getProjectList();

    //get a Project with a given projectId
    Project getProjectByID(int projectID);

    //insert a new Project to the database
    Project insertProject(Project project);

    //update a Project in the database
    Project updateProject(Project project);

    //delete a Project in the database
    void deleteProject(Project project);
}