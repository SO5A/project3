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
public class EmployeeTest {

    private Employee employee;

    @Mock
    private Task task;

    @Before
    public void setUp() {
        employee = new Employee("John Doe", "Additional Info");
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        employee.setId(id);
        assertEquals(id, employee.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("John Doe", employee.getName());
    }

    @Test
    public void testGetAdditionalInformation() {
        assertEquals("Additional Info", employee.getAdditionalInformation());
    }

    @Test
    public void testGetTasks() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(task);
        employee.setTasks(tasks);
        assertNotNull(employee.getTasks());
        assertEquals(1, employee.getTasks().size());
    }

    @Test
    public void testSetTasks() {
        Set<Task> tasks = new HashSet<>();
        tasks.add(task);
        employee.setTasks(tasks);
        assertNotNull(employee.getTasks());
        assertEquals(1, employee.getTasks().size());
    }
}
