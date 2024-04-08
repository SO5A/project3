package com.andrz.project3.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.andrz.project3.entity.Task;
import com.andrz.project3.service.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;


import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private DBFileStorageService dbFileStorageService;

    @Mock
    private CommentService commentService;

    @Before
    public void setUp() {
        taskController = new TaskController(departmentService, taskService, employeeService, dbFileStorageService, commentService);
    }

    @Test
    public void testFindAllTasks() {
        Page<Task> taskPage = mock(Page.class);
        when(taskService.findPaginated(PageRequest.of(0, 5))).thenReturn(taskPage);
        when(taskPage.getTotalPages()).thenReturn(3);

        Model model = mock(Model.class);
        String viewName = taskController.findAllTasks(model, Optional.of(1), Optional.of(5));

        assertEquals("list-tasks", viewName);
        verify(model).addAttribute("tasks", taskPage);
        verify(model).addAttribute("pageNumbers", List.of(1, 2, 3));
    }

    @Test
    public void testSearchTask() {
        List<Task> tasks = new ArrayList<>();
        when(taskService.searchTasks("keyword")).thenReturn(tasks);

        Model model = mock(Model.class);
        String viewName = taskController.searchTask("keyword",model);

        assertEquals("list-tasks", viewName);
        verify(model).addAttribute("tasks", tasks);
    }

    @Test
    public void testFindTaskById() {
        Task mockTask = new Task( "Sample Task","1");
        when(taskService.findTaskById(1L)).thenReturn(mockTask);

        Model model = mock(Model.class);
        String viewName = taskController.findTaskById(1L, model);

        assertEquals("list-task", viewName);
        verify(model).addAttribute("task", mockTask);
    }

    @Test
    public void testShowCreateForm() {
        Task mockTask = new Task( "Sample Task","1");
        Model model = mock(Model.class);
        String viewName = taskController.showCreateForm(mockTask,model);

        assertEquals("add-task", viewName);
    }

//    @Test
//    public void testCreateTask() throws IOException {
//        // Mock Task, BindingResult, Model, and TaskService
//        Task newTask = new Task("New Task", "1");
//        BindingResult bindingResult = mock(BindingResult.class);
//        Model model = mock(Model.class);
//        TaskService taskService = mock(TaskService.class);
//        DepartmentService departmentService = mock(DepartmentService.class);
//        EmployeeService employeeService = mock(EmployeeService.class);
//        DBFileStorageService dbFileStorageService = mock(DBFileStorageService.class);
//        CommentService commentService = mock(CommentService.class);
//
//        // Mock MultipartFile array
//        MultipartFile[] files = new MultipartFile[2]; // Example with 2 files
//// Create a mock MultipartFile object
//        MultipartFile file = mock(MultipartFile.class);
//// Mock the necessary behavior for the file object
//        when(file.getOriginalFilename()).thenReturn("example.txt");
//        when(file.getBytes()).thenReturn(new byte[]{1, 2, 3}); // Mocking file content
//
//// Assign the mock file to the files array
//        files[0] = file;
//
//        // Create TaskController instance with mocked dependencies
//        TaskController taskController = new TaskController(departmentService, taskService, employeeService, dbFileStorageService, commentService);
//
//        // Call the createTask method with the mock file
//        String viewName = taskController.createTask(newTask, files, bindingResult, model);
//
//        // Verify the behavior
//        assertEquals("redirect:/tasks", viewName);
//        verify(taskService).createTask(newTask);
//        verify(model).addAttribute("task", taskService.findAllTasks());
//
//        // Add additional verifications for file processing if needed
//    }
    @Test
    public void testShowUpdateForm() {
        Task mockTask = new Task("Sample Task","1");
        when(taskService.findTaskById(1L)).thenReturn(mockTask);

        Model model = mock(Model.class);
        String viewName = taskController.showUpdateForm(1L, model);

        assertEquals("update-task", viewName);
        verify(model).addAttribute("task", mockTask);
    }

//    @Test
//    public void testUpdateTask() {
//        Task updatedTask = new Task("Updated Task","1");
//        BindingResult bindingResult = mock(BindingResult.class);
//        Model model = mock(Model.class);
//
//        String viewName = taskController.updateTask(1L, updatedTask, bindingResult, model);
//
//        assertEquals("redirect:/tasks", viewName);
//        verify(taskService).updateTask(updatedTask);
//        verify(model).addAttribute("task", taskService.findAllTasks());
//    }

    @Test
    public void testDeleteTask() {
        Model model = mock(Model.class);
        String viewName = taskController.deleteTask(1L, model);

        assertEquals("redirect:/tasks", viewName);
        verify(taskService).deleteTask(1L);
        verify(model).addAttribute("task", taskService.findAllTasks());
    }

//    @Test
//    public void testAddComment() {
//        Task mockTask = new Task( "Sample Task","1");
//        Comment comment = new Comment("1",mockTask, );
//        when(taskService.findTaskById(1L)).thenReturn(mockTask);
//
//        Model model = mock(Model.class);
//        String viewName = taskController.addComment("New Comment", model, 1L);
//
//        assertEquals("redirect:/tasks", viewName);
//        verify(commentService).addCommentToTask(mockTask, "New Comment");
//    }
//
//    @Test
//    public void testDeleteComment() {
//        Task mockTask = new Task(1L, "Sample Task");
//        when(taskService.findTaskById(1L)).thenReturn(mockTask);
//
//        Model model = mock(Model.class);
//        String viewName = taskController.deleteComment(1L, 1L, model);
//
//        assertEquals("redirect:/tasks", viewName);
//        verify(commentService).deleteCommentFromTask(mockTask, 1L);
//    }
}
