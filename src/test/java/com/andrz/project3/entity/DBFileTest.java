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
public class DBFileTest {

    private DBFile dbFile;

    @Mock
    private Task task;

    @Before
    public void setUp() {
        byte[] data = new byte[]{1, 0, 1, 0}; // Example data
        dbFile = new DBFile("example.txt", "text/plain", data);
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        dbFile.setId(id);
        assertEquals(id, dbFile.getId());
    }

    @Test
    public void testGetFileName() {
        assertEquals("example.txt", dbFile.getFileName());
    }

    @Test
    public void testGetFileType() {
        assertEquals("text/plain", dbFile.getFileType());
    }

    @Test
    public void testGetData() {
        assertNotNull(dbFile.getData());
        assertEquals(4, dbFile.getData().length); // Example data length
    }

    @Test
    public void testGetTask() {
        dbFile.setTask(task);
        assertNotNull(dbFile.getTask());
        assertEquals(task, dbFile.getTask());
    }

    @Test
    public void testGetContentType() {
        String contentType = dbFile.getContentType();
        assertEquals("text/plain", contentType); // Assuming the content type is correctly guessed
    }
}

