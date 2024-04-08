package com.andrz.project3.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.andrz.project3.controller.DepartmentController;
import com.andrz.project3.entity.Department;
import com.andrz.project3.service.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentControllerTest {

    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() {
        departmentController = new DepartmentController(departmentService);
    }

    @Test
    public void testFindAllDepartments() {
        List<Department> departments = new ArrayList<>();
        when(departmentService.findAllDepartments()).thenReturn(departments);

        String viewName = departmentController.findAllDepartments(model);

        assertEquals("list-departments", viewName);
        verify(model).addAttribute("departments", departments);
    }

    @Test
    public void testFindDepartmentById() {
        Department mockDepartment = new Department("IT Department");
        when(departmentService.findDepartmentById(1L)).thenReturn(mockDepartment);

        String viewName = departmentController.findDepartmentById(1L, model);

        assertEquals("list-department", viewName);
        verify(model).addAttribute("department", mockDepartment);
    }

    @Test
    public void testShowCreateForm() {
        String viewName = departmentController.showCreateForm(new Department());

        assertEquals("add-department", viewName);
    }

    @Test
    public void testCreateDepartment() {
        Department newDepartment = new Department("HR Department");

        String viewName = departmentController.createDepartment(newDepartment, bindingResult, model);

        assertEquals("redirect:/departments", viewName);
        verify(departmentService).createDepartment(newDepartment);
        verify(model).addAttribute("department", departmentService.findAllDepartments());
    }

    @Test
    public void testShowUpdateForm() {
        Department mockDepartment = new Department("IT Department");
        when(departmentService.findDepartmentById(1L)).thenReturn(mockDepartment);

        String viewName = departmentController.showUpdateForm(1L, model);

        assertEquals("update-department", viewName);
        verify(model).addAttribute("department", mockDepartment);
    }

    @Test
    public void testUpdateDepartment() {
        Department updatedDepartment = new Department(  "Updated Department");

        String viewName = departmentController.updateDepartment(1L, updatedDepartment, bindingResult, model);

        assertEquals("redirect:/departments", viewName);
        verify(departmentService).updateDepartment(updatedDepartment);
        verify(model).addAttribute("department", departmentService.findAllDepartments());
    }

    @Test
    public void testDeleteDepartment() {
        String viewName = departmentController.deleteDepartment(1L, model);
        assertEquals("redirect:/departments", viewName);
        verify(departmentService).deleteDepartment(1L);
        verify(model).addAttribute("department", departmentService.findAllDepartments());
    }
}
