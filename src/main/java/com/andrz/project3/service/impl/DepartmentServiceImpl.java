package com.andrz.project3.service.impl;

import java.util.List;

import com.andrz.project3.entity.Department;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.andrz.project3.exception.NotFoundException;
import com.andrz.project3.repository.DepartmentRepository;
import com.andrz.project3.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	final DepartmentRepository departmentRepository;

	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Department> findAllDepartments() {
		return departmentRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Department findDepartmentById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Department not found  with ID %d", id)));
	}

	@Override
	public void createDepartment(Department department) {
		departmentRepository.save(department);
	}

	@Override
	public void updateDepartment(Department department) {
		departmentRepository.save(department);
	}

	@Override
	public void deleteDepartment(Long id) {
		var category = departmentRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Department not found  with ID %d", id)));

		departmentRepository.deleteById(category.getId());
	}

}
