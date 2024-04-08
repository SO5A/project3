package com.andrz.project3.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import com.andrz.project3.entity.DBFile;
import com.andrz.project3.service.DBFileStorageService;
import com.andrz.project3.service.FilesWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class FileControllerTest {

    private FileController fileController;

    @Mock
    private DBFileStorageService dbFileStorageService;

    @Mock
    private DBFile dbFile;

    @Before
    public void setUp() {
        fileController = new FileController(dbFileStorageService);
    }

    @Test
    public void testDownloadFile() throws IOException {
        Long fileId = 1L;
        byte[] data = "Test file content".getBytes();
        String fileName = "test.txt";
        String mimeType = "text/plain";

        when(dbFileStorageService.getFileById(fileId)).thenReturn(dbFile);
        when(dbFile.getData()).thenReturn(data);
        when(dbFile.getFileName()).thenReturn(fileName);

        FilesWrapper filesWrapper = mock(FilesWrapper.class);
        FileController fileController = new FileController(dbFileStorageService);
        ResponseEntity<Resource> response = fileController.downloadFile(fileId);

        assertEquals(MediaType.parseMediaType(mimeType), response.getHeaders().getContentType());
        assertEquals("attachment; filename=\"test.txt\"", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(new ByteArrayResource(data), response.getBody());
    }
}
