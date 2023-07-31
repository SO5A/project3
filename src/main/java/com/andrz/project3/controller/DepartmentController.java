package com.andrz.project3.controller;

import com.andrz.project3.entity.Department;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.andrz.project3.service.DepartmentService;

@Controller
public class DepartmentController {

	final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;

	}

	@RequestMapping("/departments")
	public String findAllDepartments(Model model) {

		model.addAttribute("departments", departmentService.findAllDepartments());
		return "list-departments";
	}

	@RequestMapping("/department/{id}")
	public String findDepartmentById(@PathVariable("id") Long id, Model model) {

		model.addAttribute("department", departmentService.findDepartmentById(id));
		return "list-department";
	}

	@GetMapping("/addDepartment")
	public String showCreateForm(Department department) {
		return "add-department";
	}

	@RequestMapping("/add-department")
	public String createDepartment(Department department, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-department";
		}

		departmentService.createDepartment(department);
		model.addAttribute("department", departmentService.findAllDepartments());
		return "redirect:/departments";
	}

	@GetMapping("/updateDepartment/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute("department", departmentService.findDepartmentById(id));
		return "update-department";
	}

	@RequestMapping("/update-department/{id}")
	public String updateDepartment(@PathVariable("id") Long id, Department department, BindingResult result, Model model) {
		if (result.hasErrors()) {
			department.setId(id);
			return "update-department";
		}

		departmentService.updateDepartment(department);
		model.addAttribute("department", departmentService.findAllDepartments());
		return "redirect:/departments";
	}

	@RequestMapping("/remove-department/{id}")
	public String deleteDepartment(@PathVariable("id") Long id, Model model) {
		departmentService.deleteDepartment(id);

		model.addAttribute("department", departmentService.findAllDepartments());
		return "redirect:/departments";
	}

}
