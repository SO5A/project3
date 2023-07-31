package com.andrz.project3;

import com.andrz.project3.entity.*;
import com.andrz.project3.repository.UserRepository;
import com.andrz.project3.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class Project3Application {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Project3Application.class, args);
	}
	@Bean
	public CommandLineRunner initialCreate() {
		return (args) -> {

			var task1 = new Task("Task1"," Description1");
			task1.addEmployees(new Employee("Andrew", "xwz"));
			task1.addDepartments(new Department("Department1"));
			taskService.createTask(task1);

			var task2 = new Task("Task2", "Description2");
			task2.addEmployees(new Employee("Snow", "xwz"));
			task2.addDepartments(new Department("Department2"));
			taskService.createTask(task2);

			var task3 = new Task("Task3", "Description3");
			task3.addEmployees(new Employee("Sand", "xwz"));
			task3.addDepartments(new Department("Department3"));
			taskService.createTask(task3);

			var user = new User("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"),
					Arrays.asList(new Role("ROLE_ADMIN")));
			userRepository.save(user);

		};
	}
}
