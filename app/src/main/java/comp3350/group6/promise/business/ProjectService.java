package comp3350.group6.promise.business;

import java.util.Collections;
import java.util.List;

import comp3350.group6.promise.objects.Exceptions.EmptyInputException;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.persistence.ProjectDao;
import comp3350.group6.promise.persistence.hsqldb.ProjectImp;

public class ProjectService {

    private ProjectDao projectDao;
    private List<Project> projects;
    private static ProjectService instance;

    public ProjectService(){
        projectDao = new ProjectImp();
        projects = null;
    }

    public ProjectService(ProjectDao projectDao){
        this();
        this.projectDao = projectDao;
    }

    // returns a project by its given id
    public Project getProjectByID(int id){
        return projectDao.getProjectByID(id);
    }

    //returns a read-only list of Projects
    public List<Project> getProjects(){
        projects = projectDao.getProjectList();
        return Collections.unmodifiableList(projects);
    }

    /*
        Accept only projects with valid inputs to be inserted to database.
        If projects with empty project names are being inserted,
        the insertProject will throw an EmptyInputException.
     */
    public Project insertProject(Project project) throws EmptyInputException {
        if (project.getProjectName().isEmpty()){
            throw new EmptyInputException("project name");
        }
		return projectDao.insertProject(project);
    }

    // replace an existing project in the list with the given project
    public Project updateProject(Project project) throws EmptyInputException {
        if (project.getProjectName().isEmpty()){
            throw new EmptyInputException("project name");
        }
		return projectDao.updateProject(project);
    }

    // delete an existing project in the list
    public void deleteProject(Project project){
		projectDao.deleteProject(project);
    }

    public static ProjectService getInstance() {
        if(ProjectService.instance == null) {
            ProjectService.instance = new ProjectService();
        }
        return ProjectService.instance;
    }
}