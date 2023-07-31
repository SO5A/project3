package com.andrz.project3.controller;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.andrz.project3.entity.Task;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.andrz.project3.service.EmployeeService;
import com.andrz.project3.service.TaskService;
import com.andrz.project3.service.DepartmentService;

@Controller
public class TaskController {

	final TaskService taskService;
	final EmployeeService employeeService;
	final DepartmentService departmentService;

	public TaskController(DepartmentService departmentService, TaskService taskService,
						  EmployeeService employeeService) {
		this.employeeService = employeeService;
		this.taskService = taskService;
		this.departmentService = departmentService;
	}

	@RequestMapping({ "/tasks", "/" })
	public String findAllTasks(Model model, @RequestParam("page") Optional<Integer> page,
							   @RequestParam("size") Optional<Integer> size) {

		var currentPage = page.orElse(1);
		var pageSize = size.orElse(5);

		var taskPage = taskService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("tasks", taskPage);

		var totalPages = taskPage.getTotalPages();
		if (totalPages > 0) {
			var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "list-tasks";
	}

	@RequestMapping("/searchTask")
	public String searchTask(@Param("keyword") String keyword, Model model) {

		model.addAttribute("tasks", taskService.searchTasks(keyword));
		return "list-tasks";
	}

	@RequestMapping("/task/{id}")
	public String findTaskById(@PathVariable("id") Long id, Model model) {

		model.addAttribute("task", taskService.findTaskById(id));
		return "list-task";
	}

	@GetMapping("/add")
	public String showCreateForm(Task task, Model model) {

		model.addAttribute("departments", departmentService.findAllDepartments());
		model.addAttribute("employees", employeeService.findAllEmployees());
		return "add-task";
	}

	@RequestMapping("/add-task")
	public String createTask(Task task, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-task";
		}

		taskService.createTask(task);
		model.addAttribute("task", taskService.findAllTasks());
		return "redirect:/tasks";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute("task", taskService.findTaskById(id));
		return "update-task";
	}

	@RequestMapping("/update-task/{id}")
	public String updateTask(@PathVariable("id") Long id, Task task, BindingResult result, Model model) {
		if (result.hasErrors()) {
			task.setId(id);
			return "update-task";
		}

		taskService.updateTask(task);
		model.addAttribute("task", taskService.findAllTasks());
		return "redirect:/tasks";
	}

	@RequestMapping("/remove-task/{id}")
	public String deleteTask(@PathVariable("id") Long id, Model model) {
		taskService.deleteTask(id);

		model.addAttribute("task", taskService.findAllTasks());
		return "redirect:/tasks";
	}

}
