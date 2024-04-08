package com.andrz.project3.entity;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)

public class TaskTest {

    private Task task;

    @Mock
    private Employee employee;

    @Mock
    private User user;

    @Mock
    private Department department;

    @Before
    public void setUp() {
        task = new Task("Test Task", "Description");
        employee = new Employee("Test Employee", "Test Employee");
        department = new Department("Test Department");
    }

    @Test
    public void testAddDbFile() {
        DBFile dbFile = new DBFile("file.txt", "text/plain", new byte[0]);
        task.addDbFile(dbFile);
        assertEquals(1, task.getDbFiles().size());
        assertEquals(task, dbFile.getTask());
    }

    @Test
    public void testAddEmployees() {
        task.addEmployees(employee);
        Set<Task> tasks = employee.getTasks();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.iterator().next());
    }

    @Test
    public void testRemoveEmployees() {
        task.addEmployees(employee);
        task.removeEmployees(employee);
        Set<Task> tasks = employee.getTasks();
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    @Test
    public void testAddDepartments() {
        task.addDepartments(department);
        Set<Task> tasks = department.getTasks();
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals(task, tasks.iterator().next());
    }

    @Test
    public void testRemoveDepartments() {
        task.addDepartments(department);
        task.removeDepartments(department);
        Set<Task> tasks = department.getTasks();
        assertNotNull(tasks);
        assertEquals(0, tasks.size());
    }

    @Test
    public void testAddComment() {
        Comment comment = new Comment("Test Comment", task, user);
        task.addComment(comment);
        assertEquals(1, task.getComments().size());
        assertEquals(task, comment.getTask());
    }

    @Test
    public void testRemoveComment() {
        Comment comment = new Comment("Test Comment", task, user);
        task.addComment(comment);
        task.removeComment(comment);
        assertEquals(0, task.getComments().size());
        assertEquals(null, comment.getTask());
    }
}

