package com.andrz.project3.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.andrz.project3.entity.Employee;
import com.andrz.project3.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() {
        employeeController = new EmployeeController(employeeService);
    }

    @Test
    public void testFindAllEmployees() {
        Page<Employee> employeePage = mock(Page.class);
        when(employeeService.findPaginated(PageRequest.of(0, 5))).thenReturn(employeePage);
        when(employeePage.getTotalPages()).thenReturn(3);

        String viewName = employeeController.findAllEmployees(model, Optional.of(1), Optional.of(5));

        assertEquals("list-employees", viewName);
        verify(model).addAttribute("employees", employeePage);
        verify(model).addAttribute("pageNumbers", List.of(1, 2, 3));
    }
    @Test
    public void testFindEmployeeById() {
        Employee mockEmployee = new Employee( "John Doe","1");
        when(employeeService.findEmployeeById(1L)).thenReturn(mockEmployee);

        String viewName = employeeController.findEmployeeById(1L, model);

        assertEquals("list-employee", viewName);
        verify(model).addAttribute("employee", mockEmployee);
    }

    @Test
    public void testShowCreateForm() {
        String viewName = employeeController.showCreateForm(new Employee("John Doe","1"));

        assertEquals("add-employee", viewName);
    }

    @Test
    public void testCreateEmployee() {
        Employee newEmployee = new Employee("Alice Johnson", "2");

        String viewName = employeeController.createEmployee(newEmployee, bindingResult, model);

        assertEquals("redirect:/employees", viewName);
        verify(employeeService).createEmployee(newEmployee);
        verify(model).addAttribute("employee", employeeService.findAllEmployees());
    }

    @Test
    public void testShowUpdateForm() {
        Employee mockEmployee = new Employee("John Doe", "1");
        when(employeeService.findEmployeeById(1L)).thenReturn(mockEmployee);

        String viewName = employeeController.showUpdateForm(1L, model);

        assertEquals("update-employee", viewName);
        verify(model).addAttribute("employee", mockEmployee);
    }

    @Test
    public void testUpdateEmployee() {
        Employee updatedEmployee = new Employee("Updated Name", "1");

        String viewName = employeeController.updateEmployee(1L, updatedEmployee, bindingResult, model);

        assertEquals("redirect:/employees", viewName);
        verify(employeeService).updateEmployee(updatedEmployee);
        verify(model).addAttribute("employee", employeeService.findAllEmployees());
    }

    @Test
    public void testDeleteEmployee() {
        String viewName = employeeController.deleteEmployee(1L, model);

        assertEquals("redirect:/employees", viewName);
        verify(employeeService).deleteEmployee(1L);
        verify(model).addAttribute("employee", employeeService.findAllEmployees());
    }
}
