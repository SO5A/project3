package com.andrz.project3.service;

import com.andrz.project3.entity.Task;
import com.andrz.project3.repository.TaskRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;



import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@SpringBootTest
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void testFindAllTasks() {
        List<Task> tasks = Arrays.asList(new Task("1", "Task 1"), new Task("2", "Task 2"));
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.findAllTasks();

        assertEquals(2, result.size());
    }

    @Test
    public void testSearchTasks_WithKeyword() {
        List<Task> tasks = Arrays.asList(new Task("1", "Task 1"), new Task("2", "Task 2"));
        when(taskRepository.search("Task")).thenReturn(tasks);

        List<Task> result = taskService.searchTasks("Task");

        assertEquals(2, result.size());
    }

    @Test
    public void testSearchTasks_WithoutKeyword() {
        List<Task> tasks = Arrays.asList(new Task("1", "Task 1"), new Task("2", "Task 2"));
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.searchTasks(null);

        assertEquals(2, result.size());
    }

    @Test
    public void testFindTaskById() {
        Task task = new Task("1", "Task 1");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.findTaskById(1L);

        assertEquals("Task 1", result.getTitle());
    }

    @Test
    public void testCreateTask() {
        Task task = new Task("1", "New Task");

        taskService.createTask(task);

        Mockito.verify(taskRepository).save(task);
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task("1", "Updated Task");

        taskService.updateTask(task);

        Mockito.verify(taskRepository).save(task);
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task("1", "Task 1");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        Mockito.verify(taskRepository).deleteById(1L);
    }

    @Test
    public void testFindPaginated() {
        List<Task> tasks = Arrays.asList(new Task("1", "Task 1"), new Task("2", "Task 2"));
        when(taskRepository.findAll()).thenReturn(tasks);

        Page<Task> result = taskService.findPaginated(PageRequest.of(0, 1));

        assertEquals(1, result.getContent().size());
        assertEquals(2, result.getTotalElements());
    }
}