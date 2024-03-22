package com.andrz.project3.controller;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.andrz.project3.entity.Comment;
import com.andrz.project3.entity.DBFile;
import com.andrz.project3.entity.Task;
import com.andrz.project3.entity.User;
import com.andrz.project3.service.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@Controller
public class TaskController {

	final TaskService taskService;
	final EmployeeService employeeService;
	final DepartmentService departmentService;
	final DBFileStorageService dbFileStorageService;
	final CommentService commentService;

	final UserService userService;

	public TaskController(DepartmentService departmentService, TaskService taskService,
						  EmployeeService employeeService, DBFileStorageService dbFileStorageService, CommentService commentService) {
		this.employeeService = employeeService;
		this.taskService = taskService;
		this.departmentService = departmentService;
		this.dbFileStorageService = dbFileStorageService;
		this.commentService = commentService;
		userService = null;
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
		model.addAttribute("text", "");
		return "list-task";
	}

	@GetMapping("/add")
	public String showCreateForm(Task task, Model model) {

		model.addAttribute("departments", departmentService.findAllDepartments());
		model.addAttribute("employees", employeeService.findAllEmployees());
		return "add-task";
	}


@RequestMapping("/add-task")
public String createTask(Task task, @RequestParam("file") MultipartFile[] files, BindingResult result, Model model) {
	if (result.hasErrors()) {
		return "add-task";
	}


	taskService.createTask(task);
	for (MultipartFile file : files) {
		DBFile dbFile = dbFileStorageService.storeFile(file, task);
		task.addDbFile(dbFile);
	}

	taskService.updateTask(task);

	return "redirect:/tasks";
}
	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute("task", taskService.findTaskById(id));
		return "update-task";
	}

	@RequestMapping("/update-task/{id}")
	public String updateTask(@PathVariable("id") Long id, Task task, @RequestParam("file") MultipartFile[] files, BindingResult result, Model model) {
		if (result.hasErrors()) {
			task.setTask_id(id);
			return "update-task";
		}
		for (MultipartFile file : files) {
			DBFile dbFile = dbFileStorageService.storeFile(file, task);
			task.addDbFile(dbFile);
		}
		taskService.updateTask(task);
		return "redirect:/tasks";
	}

	@RequestMapping("/remove-task/{id}")
	public String deleteTask(@PathVariable("id") Long id, Model model) {
		taskService.deleteTask(id);

		model.addAttribute("task", taskService.findAllTasks());
		return "redirect:/tasks";
	}
	@PostMapping("/tasks/{id}/comments")
	public String addComment(@ModelAttribute Comment comment, Model model, @PathVariable Long id) {
		// Get the task by ID
		Task task = taskService.findTaskById(id);
		if (task == null) {
			return "redirect:/tasks";
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();


		return "redirect:/tasks";
	}

	@DeleteMapping("/comments/{commentId}")
	public String deleteComment(@PathVariable Long commentId) {
		// Get the comment by ID
		Comment comment = commentService.findCommentById(commentId);
		if (comment == null) {
			return "redirect:/tasks";
		}

		// Delete the comment
		commentService.deleteComment(commentId);

		return "redirect:/tasks";
	}
}