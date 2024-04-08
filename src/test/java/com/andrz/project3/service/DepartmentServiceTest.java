package com.andrz.project3.service;

import com.andrz.project3.entity.Department;
import com.andrz.project3.repository.DepartmentRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    public void testFindAllDepartments() {
        List<Department> departments = Arrays.asList(new Department("HR"), new Department("IT"));
        when(departmentRepository.findAll()).thenReturn(departments);

        List<Department> result = departmentService.findAllDepartments();

        assertEquals(2, result.size());
    }

    @Test
    public void testFindDepartmentById() {
        Department department = new Department("HR");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        Department result = departmentService.findDepartmentById(1L);

        assertEquals("HR", result.getName());
    }

    @Test
    public void testCreateDepartment() {
        Department department = new Department( "IT");

        departmentService.createDepartment(department);

        Mockito.verify(departmentRepository).save(department);
    }

    @Test
    public void testUpdateDepartment() {
        Department department = new Department("Finance");

        departmentService.updateDepartment(department);

        Mockito.verify(departmentRepository).save(department);
    }

    @Test
    public void testDeleteDepartment() {
        Department department = new Department("Finance");
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));

        departmentService.deleteDepartment(1L);

        Mockito.verify(departmentRepository).deleteById(1L);
    }
}