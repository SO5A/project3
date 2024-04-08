package com.andrz.project3.service;

import com.andrz.project3.entity.Comment;
import com.andrz.project3.entity.Task;
import com.andrz.project3.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    private Task task;
    private User user;
    private CommentService commentService;
    private Comment newComment;
    @Before public void setUp() {
        task = new Task();
        user = new User();
        commentService = mock(CommentService.class);
        newComment = new Comment("New Comment", task, user);
    }

    @Test
    public void testFindCommentById() {

        // Define the behavior of the findCommentById method
        when(commentService.findCommentById(1L)).thenReturn(newComment);

        // Call the findCommentById method and assert the result
        Comment result = commentService.findCommentById(1L);
        assertEquals("New Comment", result.getText());
    }

    @Test
    public void testDeleteComment() {
        // Call the deleteComment method
        commentService.deleteComment(1L);

        // Verify that the deleteComment method was called with the correct argument
        Mockito.verify(commentService, times(1)).deleteComment(1L);
    }

    @Test
    public void testCreateComment() {
        // Define the behavior of the createComment method
        when(commentService.createComment(any(Comment.class))).thenReturn(newComment);

        // Call the createComment method with the new Comment object
        Comment result = commentService.createComment(newComment);

        // Assert that the returned Comment object matches the expected newComment
        assertEquals("New Comment", result.getText());
        assertEquals(task, result.getTask());
        assertEquals(user, result.getUser());
    }
}