package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

//TODO
public class TestProject {

    Task task1, task2, task3;
    Project project, project1;

    @BeforeEach
    void runBefore () {
        task1 = new Task("task");
        task2 = new Task("task");
        task3 = new Task("task");
        task2.setStatus(Status.DONE);
        task3.setStatus(Status.DONE);
        project = new Project("project");
        project1 = new Project("project");
    }

    @Test
    void testGetEstimatedTime() {
        task1.setEstimatedTimeToComplete(5);
        task2.setEstimatedTimeToComplete(10);
        project.add(task1);
        project.add(task2);
        project1.add(task3);
        project.add(project1);
        task3.setEstimatedTimeToComplete(5);
        task3.setEstimatedTimeToComplete(2);
        assertEquals(17, project.getEstimatedTimeToComplete());
        int hours = 0;
        for (Todo todo: project) {
            hours += todo.getEstimatedTimeToComplete();
        }
        assertEquals(17, hours);
    }

    @Test
    void testGetProgress() {
        task1.setProgress(100);
        task2.setProgress(20);
        project.add(task1);
        project1.add(task2);
        project.add(project1);
        assertEquals(60, project.getProgress());
        for (Todo todo: project) {
        }
    }

}