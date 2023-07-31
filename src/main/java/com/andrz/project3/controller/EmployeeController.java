package com.andrz.project3.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.andrz.project3.entity.Employee;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.andrz.project3.service.EmployeeService;

@Controller
public class EmployeeController {

	final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@RequestMapping("/employees")
	public String findAllEmployees(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size) {

		var currentPage = page.orElse(1);
		var pageSize = size.orElse(5);
		var employeePage = employeeService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("employees", employeePage);

		int totalPages = employeePage.getTotalPages();
		if (totalPages > 0) {
			var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "list-employees";
	}

	@RequestMapping("/employee/{id}")
	public String findEmployeeById(@PathVariable("id") Long id, Model model) {

		model.addAttribute("employee", employeeService.findEmployeeById(id));
		return "list-employee";
	}

	@GetMapping("/addEmployee")
	public String showCreateForm(Employee employee) {
		return "add-employee";
	}

	@RequestMapping("/add-employee")
	public String createEmployee(Employee employee, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-employee";
		}

		employeeService.createEmployee(employee);
		model.addAttribute("employee", employeeService.findAllEmployees());
		return "redirect:/employees";
	}

	@GetMapping("/updateEmployee/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute("employee", employeeService.findEmployeeById(id));
		return "update-employee";
	}

	@RequestMapping("/update-employee/{id}")
	public String updateEmployee(@PathVariable("id") Long id, Employee employee, BindingResult result, Model model) {
		if (result.hasErrors()) {
			employee.setId(id);
			return "update-employee";
		}

		employeeService.updateEmployee(employee);
		model.addAttribute("employee", employeeService.findAllEmployees());
		return "redirect:/employees";
	}

	@RequestMapping("/remove-employee/{id}")
	public String deleteEmployee(@PathVariable("id") Long id, Model model) {
		employeeService.deleteEmployee(id);

		model.addAttribute("employee", employeeService.findAllEmployees());
		return "redirect:/employees";
	}

}
