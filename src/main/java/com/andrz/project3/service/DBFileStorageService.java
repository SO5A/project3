package com.andrz.project3.service;

import com.andrz.project3.entity.DBFile;
import com.andrz.project3.entity.Task;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface DBFileStorageService {

    public DBFile storeFile(MultipartFile file, Task task);

//    public DBFile getFile(String fileId);

    DBFile getFileById(Long fileId) throws FileNotFoundException;

//    public List<DBFile> getFiles(String taskId);
}