package com.andrz.project3.service;

import java.util.List;

import com.andrz.project3.entity.Department;

public interface DepartmentService {

	public List<Department> findAllDepartments();

	public Department findDepartmentById(Long id);

	public void createDepartment(Department department);

	public void updateDepartment(Department department);

	public void deleteDepartment(Long id);

}
