package comp3350.group6.promise.persistence.stub;

import java.util.List;

import comp3350.group6.promise.objects.FakeDB;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.persistence.ProjectDao;

public class ProjectImpNoDB implements ProjectDao {

    @Override
    public List<Project> getProjectList() {
        return FakeDB.projects;
    }

    @Override
    public Project getProjectByID(int id){ return FakeDB.projects.get(getIndexByID(id)); }

    @Override
    public Project insertProject(Project project){

        FakeDB.projects.add(project);

        return project;
    }


    // Try and replace a project with the given project object if it exists in the project list.
    @Override
    public Project updateProject(Project project){
        int index = getIndexByID(project.getProjectID());

        if (index != -1){ // project exists in our list
            FakeDB.projects.set(index, project);
        }

        return project;
    }

    // Try and delete a project if it exists in the project list.
    @Override
    public void deleteProject(Project project){
        int index = getIndexByID(project.getProjectID());

        if (index != -1){
            FakeDB.projects.remove(index);
        }
    }

    private int getIndexByID(int id){
        int index = -1;
        boolean found = false;

        for(int i = 0; i < FakeDB.projects.size() && !found; i++){
            if(FakeDB.projects.get(i).getProjectID() == id){
                found = true;
                index = i;
            }
        }

        return index;
    }
}
