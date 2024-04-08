package com.andrz.project3.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentTest {

    private Department department;

    @Mock
    private Task task;

    @Before
    public void setUp() {
        department = new Department( "Engineering");
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        department.setId(id);
        assertEquals(id, department.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Engineering", department.getName());
    }

    @Test
    public void testGetTasks() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(task);
        department.setTasks(tasks);
        assertNotNull(department.getTasks());
        assertEquals(1, department.getTasks().size());
    }

    @Test
    public void testSetTasks() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(task);
        department.setTasks(tasks);
        assertNotNull(department.getTasks());
        assertEquals(1, department.getTasks().size());
    }
}
