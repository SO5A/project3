package com.andrz.project3.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserTest {
    @Mock
    private User user;

    @Mock
    private Role role;

    @Mock
    private Comment comment;

    @Before
    public void setUp() {
        Collection<Role> roles = new ArrayList<>();
        roles.add(role);
        user = new User("John", "Doe", "john.doe@example.com", "password123", roles);
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        user.setId(id);
        assertEquals(id, user.getId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("John", user.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("john.doe@example.com", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testGetRoles() {
        assertNotNull(user.getRoles());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void testGetComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        user.setComments(comments);
        assertNotNull(user.getComments());
        assertEquals(1, user.getComments().size());
    }

    @Test
    public void testAddComment() {

        Comment testComment = new Comment( "Great post!", new Task("1", "Sample Task"), user);

        // Act
        user.addComment(testComment);

        // Assert
        List<Comment> comments = user.getComments();
        assertEquals(1, comments.size());
        assertTrue(comments.contains(testComment));
        assertEquals(user, testComment.getUser());
    }

    @Test
    public void testRemoveComment() {
        user.addComment(comment);
        user.removeComment(comment);
        assertEquals(0, user.getComments().size());
        assertEquals(null, comment.getUser());
    }
}