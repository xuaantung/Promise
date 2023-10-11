package comp3350.group6.promise.tests.objects;

import org.junit.Test;
import java.sql.Timestamp;

import comp3350.group6.promise.objects.Project;

import static org.junit.Assert.*;

public class ProjectTest
{
	@Test
	public void testProjectConstruct1()
	{
		Project project;

		System.out.println("\nStarting testProjectConstruct1");

		project = new Project("testProject", "This is a test project statement.");

		assertNotNull(project);
		assertTrue("testProject".equals(project.getProjectName()));
		assertTrue("This is a test project statement.".equals(project.getStatement()));

		System.out.println("Finished testProjectConstruct1");
	}

	@Test
	public void testProjectConstruct2()
	{
		Project project;

		System.out.println("\nStarting testProjectConstruct2");

		// int projectID, String projectName, String statement, int statusNum, Timestamp createdTime, Timestamp estimatedEndTime
		Timestamp time = new Timestamp(System.currentTimeMillis());
		project = new Project(1, "testProject", "This is a test project statement.", 0, time, time);

		assertNotNull(project);
		assertTrue(1 == project.getProjectID());
		assertTrue("testProject".equals(project.getProjectName()));
		assertTrue("This is a test project statement.".equals(project.getStatement()));
		assertTrue(0 == project.getStatusNum());
		assertTrue(time.equals(project.getCreatedTime()));
		assertTrue(time.equals(project.getEstimatedEndTime()));

		System.out.println("Finished testProjectConstruct2");
	}

	@Test
	public void testProjectSet()
	{
		Project project;

		System.out.println("\nStarting testProjectSet");

		project = new Project("testProject", "This is a test project statement.");
		Timestamp time = new Timestamp(System.currentTimeMillis());

		project.setProjectName("somethingElse");
		project.setStatement("This project for projects is awesome.");
		project.setStatusNum(5);
		project.setEstimatedEndTime(time);

		assertNotNull(project);
		assertTrue("somethingElse".equals(project.getProjectName()));
		assertTrue("This project for projects is awesome.".equals(project.getStatement()));
		assertTrue(5 == project.getStatusNum());
		assertTrue(time.equals(project.getEstimatedEndTime()));

		System.out.println("Finished testProjectSet");
	}
}
