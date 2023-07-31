package com.andrz.project3.service.impl;

import java.util.Collections;
import java.util.List;

import com.andrz.project3.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.andrz.project3.exception.NotFoundException;
import com.andrz.project3.repository.EmployeeRepository;
import com.andrz.project3.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	final EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Employee> findAllEmployees() {
		return employeeRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Employee findEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Employee not found with ID %d", id)));
	}

	@Override
	public void createEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	public void updateEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(Long id) {
		var employee = employeeRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Employee not found with ID %d", id)));

		employeeRepository.deleteById(employee.getId());
	}

	@Override
	public Page<Employee> findPaginated(Pageable pageable) {

		var pageSize = pageable.getPageSize();
		var currentPage = pageable.getPageNumber();
		var startItem = currentPage * pageSize;
		List<Employee> list;

		if (findAllEmployees().size() < startItem) {
			list = Collections.emptyList();
		} else {
			var toIndex = Math.min(startItem + pageSize, findAllEmployees().size());
			list = findAllEmployees().subList(startItem, toIndex);
		}

		return new PageImpl<Employee>(list, PageRequest.of(currentPage, pageSize), findAllEmployees().size());

	}

}
