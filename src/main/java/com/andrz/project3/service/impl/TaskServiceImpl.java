package com.andrz.project3.service.impl;

import java.util.Collections;
import java.util.List;

import com.andrz.project3.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.andrz.project3.exception.NotFoundException;
import com.andrz.project3.repository.TaskRepository;
import com.andrz.project3.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	final TaskRepository taskRepository;

	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Task> findAllTasks() {
		return taskRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Task> searchTasks(String keyword) {
		if (keyword != null) {
			return taskRepository.search(keyword);
		}
		return taskRepository.findAll();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Task findTaskById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Task not found with ID %d", id)));
	}

	@Override
	public void createTask(Task task) {
		taskRepository.save(task);
	}

	@Override
	public void updateTask(Task task) {
		taskRepository.save(task);
	}

	@Override
	public void deleteTask(Long id) {
		var task = taskRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Task not found with ID %d", id)));

		taskRepository.deleteById(task.getId());
	}

	@Override
	public Page<Task> findPaginated(Pageable pageable) {

		var pageSize = pageable.getPageSize();
		var currentPage = pageable.getPageNumber();
		var startItem = currentPage * pageSize;
		List<Task> list;

		if (findAllTasks().size() < startItem) {
			list = Collections.emptyList();
		} else {
			var toIndex = Math.min(startItem + pageSize, findAllTasks().size());
			list = findAllTasks().subList(startItem, toIndex);
		}

		var taskPage = new PageImpl<Task>(list, PageRequest.of(currentPage, pageSize), findAllTasks().size());

		return taskPage;
	}

}
