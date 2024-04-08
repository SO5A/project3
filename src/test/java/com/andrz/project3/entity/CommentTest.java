package com.andrz.project3.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommentTest {

    private Comment comment;

    @Mock
    private Task task;

    @Mock
    private User user;

    @Before
    public void setUp() {
        comment = new Comment("Test Comment", task, user);
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        comment.setId(id);
        assertEquals(id, comment.getId());
    }

    @Test
    public void testGetText() {
        assertEquals("Test Comment", comment.getText());
    }

    @Test
    public void testGetTask() {
        assertNotNull(comment.getTask());
        assertEquals(task, comment.getTask());
    }

    @Test
    public void testSetTask() {
        Task newTask = mock(Task.class);
        comment.setTask(newTask);
        assertEquals(newTask, comment.getTask());
    }

    @Test
    public void testGetUser() {
        assertNotNull(comment.getUser());
        assertEquals(user, comment.getUser());
    }

    @Test
    public void testSetUser() {
        User newUser = mock(User.class);
        comment.setUser(newUser);
        assertEquals(newUser, comment.getUser());
    }
}

