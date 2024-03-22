package com.andrz.project3.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long task_id;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "description", length = 250, nullable = false)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinTable(name = "tasks_employees", joinColumns = { @JoinColumn(name = "task_id") }, inverseJoinColumns = {
			@JoinColumn(name = "employee_id") })
	private Set<Employee> employees = new HashSet<Employee>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "tasks_departments", joinColumns = { @JoinColumn(name = "task_id") }, inverseJoinColumns = {
			@JoinColumn(name = "department_id") })
	private Set<Department> departments = new HashSet<Department>();

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DBFile> dbFiles = new ArrayList<>();

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();
	public Task() {
	}
	public Task(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public void addDbFile(DBFile file) {
		dbFiles.add(file);
		file.setTask(this);
	}
	public void addEmployees(Employee employee) {
		this.employees.add(employee);
		employee.getTasks().add(this);
	}

	public void removeEmployees(Employee employee) {
		this.employees.remove(employee);
		employee.getTasks().remove(this);
	}

	public void addDepartments(Department department) {
		this.departments.add(department);
		department.getTasks().add(this);
	}

	public void removeDepartments(Department department) {
		this.departments.remove(department);
		department.getTasks().remove(this);
	}

	public List<DBFile> getDbFiles() {
		return dbFiles;
	}

	public void setDbFiles(List<DBFile> dbFiles) {
		this.dbFiles = dbFiles;
	}

	public Long getTask_id() {
		return task_id;
	}

	public void setTask_id(Long task_id) {
		this.task_id = task_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setTask(this);
	}

	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setTask(null);
	}
}