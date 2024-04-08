package com.andrz.project3.service;

import com.andrz.project3.entity.DBFile;
import com.andrz.project3.entity.Task;
import com.andrz.project3.repository.DBFileRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DBFileStorageServiceTest {

    @Mock
    private DBFileRepository dbFileRepository;

    @InjectMocks
    private DBFileStorageService dbFileStorageService;

    @Test
    public void testStoreFile() throws IOException {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("test.txt");
        when(file.getContentType()).thenReturn("text/plain");
        when(file.getBytes()).thenReturn(new byte[0]);

        DBFile dbFile = new DBFile("test.txt", "text/plain", new byte[0]);
        when(dbFileRepository.save(Mockito.any(DBFile.class))).thenReturn(dbFile);

        DBFile result = dbFileStorageService.storeFile(file, new Task());

        assertEquals("test.txt", result.getFileName());
        assertEquals("text/plain", result.getFileType());
    }

    @Test
    public void testGetFileById() throws FileNotFoundException {
        DBFile dbFile = new DBFile("test.txt", "text/plain", new byte[0]);
        when(dbFileRepository.findById(1L)).thenReturn(Optional.of(dbFile));

        DBFile result = dbFileStorageService.getFileById(1L);

        assertEquals("test.txt", result.getFileName());
        assertEquals("text/plain", result.getFileType());
    }

    @Test
    public void testGetFileById_FileNotFound() {
        when(dbFileRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FileNotFoundException.class, () -> dbFileStorageService.getFileById(1L));
    }
}