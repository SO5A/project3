package com.andrz.project3.service;

import java.util.List;

import com.andrz.project3.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

	public List<Employee> findAllEmployees();

	public Employee findEmployeeById(Long id);

	public void createEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public void deleteEmployee(Long id);

	public Page<Employee> findPaginated(Pageable pageable);

}
