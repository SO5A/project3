package com.andrz.project3.service;

import java.util.List;

import com.andrz.project3.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

	public List<Task> findAllTasks();

	public List<Task> searchTasks(String keyword);

	public Task findTaskById(Long id);

	public void createTask(Task task);

	public void updateTask(Task task);

	public void deleteTask(Long id);

	public Page<Task> findPaginated(Pageable pageable);

}
