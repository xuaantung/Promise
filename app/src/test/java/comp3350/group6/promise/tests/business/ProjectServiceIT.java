package comp3350.group6.promise.tests.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import comp3350.group6.promise.business.ProjectService;
import comp3350.group6.promise.objects.Exceptions.EmptyInputException;
import comp3350.group6.promise.objects.Project;
import comp3350.group6.promise.util.DBConnectorUtil;

public class ProjectServiceIT {

    private ProjectService projectService;

    @Before
    public void setUp() {
        DBConnectorUtil.initialLocalDB();
        projectService = new ProjectService();
    }

    @After
    public void clean(){
        DBConnectorUtil.cleanLocalDB();
    }

    /*
        Test inserting a valid project object to the projectService class.
        The object in the list should be equal to the object inserted and,
        the size of the list of projects should be increased by 1.
     */
    @Test
    public void testInsertProject() throws EmptyInputException {

        System.out.println("Testing insertProject method from ProjectService Class.");
        List<Project> projects = projectService.getProjects();
        Project p = new Project("Test Project", "This is a test.");
        int originalSize = projects.size();

        projectService.insertProject(p);

        assertTrue("Inserted object should be equal to project in List.", (projectService.getProjects()).get(0).equals(p));
        assertEquals("List size should be different after insertion.", originalSize+1, projectService.getProjects().size());

        System.out.println("Passed insertProject method from ProjectService Class.");
    }

    /*
        Test inserting an invalid project object to the projectService class (no project name).
        The insertProject should throw and exception and,
        the size of the project list should remain the same, as nothing should be added to list.
    */
    @Test
    public void testEmptyInput() {

        System.out.println("Testing insertProject method from ProjectService Class with empty project name.");

        List<Project> projects = projectService.getProjects();
        Project p = new Project("", "This is a test.");

        Exception exception = assertThrows(EmptyInputException.class, () -> {
            projectService.insertProject(p);
        });

        String expectedMessage = "The field [project name] should not be empty.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals("Project list size should remain the same.", projectService.getProjects().size(), projects.size());

        System.out.println("Passed insertProject method with empty project name from ProjectService Class.");
    }

    /*
        Test updating a project object that exists in the database.
        The update should be applied to the object in the database and,
        the size of the list should remain the same.
    */
    @Test
    public void testUpdateProject() throws EmptyInputException {

        System.out.println("Testing updateProject method from ProjectService Class.");

        List<Project> projects = projectService.getProjects();
        Project target;
        int originalSize = projects.size();

        if (!projects.isEmpty()){ //make sure our list is not empty

            int index = 0; //target's index
            target = projects.get(index);
            target.setStatement("A changed Statement.");
            projectService.updateProject(target);
            assertEquals("Updated object should be equal to project in List.", "A changed Statement.", projectService.getProjects().get(index).getStatement());
            assertEquals("List size should not be changed after update.", originalSize, projectService.getProjects().size());

        }


        System.out.println("Passed updateProject method from ProjectService Class.");
    }

    /*
        Test deleting a project object that exists in the database.
        The delete should be applied to the object in the database and,
        the size of the list should be equal to original - 1 after deletion.
    */
    @Test
    public void testDeleteProject() {

        System.out.println("Testing deleteProject method from ProjectService Class.");

        List<Project> projects = projectService.getProjects();
        Project target;
        int originalSize = projects.size();

        if (!projects.isEmpty()){ //make sure our list is not empty

            int index = 0; //target's index
            target = projects.get(index);
            projectService.deleteProject(target);
            assertNotEquals("Deleted object should be not equal to project in List.", target, projectService.getProjects().get(index));
            assertEquals("List size should be different after deletion.", originalSize-1, projectService.getProjects().size());

        }


        System.out.println("Passed deleteProject method from ProjectService Class.");
    }

    @Test
    public void testGetProjectById() throws EmptyInputException{

        System.out.println("Testing getProjectId from ProjectService Class.");

        List<Project> projects;

        Project p1 = new Project("Test Project1", "This is a test 1.");
        Project p2 = new Project("Test Project2", "This is a test 2.");

        Project target; // expected project
        Project result; // returned project

        projectService.insertProject(p1);
        projectService.insertProject(p2);

        projects = projectService.getProjects();
        target = projects.get(0);
        result = projectService.getProjectByID(target.getProjectID());

        assertEquals(target, result);

        System.out.println("Passed getProjectId from ProjectService Class.");
    }
}
