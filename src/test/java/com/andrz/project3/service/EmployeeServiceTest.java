package com.andrz.project3.service;

import com.andrz.project3.entity.Employee;
import com.andrz.project3.repository.EmployeeRepository;
import com.andrz.project3.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    @Test
    public void testFindAllEmployees() {
        // Create a mock list of employees
        List<Employee> mockEmployees = Arrays.asList(new Employee("John Doe", "1"), new Employee("Jane Smith", "2"));

        // Create an EmployeeService implementation
        EmployeeService employeeService = mock(EmployeeService.class);

        // Define the behavior of the findAllEmployees method
        when(employeeService.findAllEmployees()).thenReturn(mockEmployees);

        // Call the findAllEmployees method and assert the result
        List<Employee> result = employeeService.findAllEmployees();
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test
    public void testFindEmployeeById() {
        // Create a mock Employee object for testing
        Employee mockEmployee = new Employee("John Doe", "1");

        // Create an EmployeeService implementation
        EmployeeService employeeService = mock(EmployeeService.class);

        // Define the behavior of the findEmployeeById method
        when(employeeService.findEmployeeById(1L)).thenReturn(mockEmployee);

        // Call the findEmployeeById method and assert the result
        Employee result = employeeService.findEmployeeById(1L);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testCreateEmployee() {
        // Create a mock Employee object for testing
        Employee newEmployee = new Employee("Alice Johnson","3");

        // Create an EmployeeService implementation
        EmployeeService employeeService = mock(EmployeeService.class);

        // Call the createEmployee method
        employeeService.createEmployee(newEmployee);

        // Verify that the createEmployee method was called with the correct argument
        Mockito.verify(employeeService, times(1)).createEmployee(newEmployee);
    }

    @Test
    public void testUpdateEmployee() {
        // Create a mock Employee object for testing
        Employee updatedEmployee = new Employee("1", "Updated Name");

        // Create an EmployeeService implementation
        EmployeeService employeeService = mock(EmployeeService.class);

        // Call the updateEmployee method
        employeeService.updateEmployee(updatedEmployee);

        // Verify that the updateEmployee method was called with the correct argument
        verify(employeeService, times(1)).updateEmployee(updatedEmployee);
    }

    @Test
    public void testDeleteEmployee() {
        // Create an Employee ID for testing
        Long employeeId = 2L;

        // Create an EmployeeService implementation
        EmployeeService employeeService = mock(EmployeeService.class);

        // Call the deleteEmployee method
        employeeService.deleteEmployee(employeeId);

        // Verify that the deleteEmployee method was called with the correct argument
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    public void testFindPaginated() {
        // Create a mock Page object for testing
        Page<Employee> mockPage = mock(Page.class);

        // Create a mock Pageable object for testing
        Pageable pageable = mock(Pageable.class);

        // Create an EmployeeService implementation
        EmployeeService employeeService = mock(EmployeeService.class);

        // Define the behavior of the findPaginated method
        when(employeeService.findPaginated(pageable)).thenReturn(mockPage);

        // Call the findPaginated method and assert the result
        Page<Employee> result = employeeService.findPaginated(pageable);
        assertNotNull(result);
        assertEquals(mockPage, result);
    }
}