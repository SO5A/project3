package com.andrz.project3.service.impl;

import com.andrz.project3.entity.DBFile;
import com.andrz.project3.entity.Task;
import com.andrz.project3.exception.FileStorageException;

import com.andrz.project3.repository.DBFileRepository;
import com.andrz.project3.service.DBFileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Service
public class DBFileStorageServiceImpl implements DBFileStorageService {

    final DBFileRepository dbFileRepository;

    public DBFileStorageServiceImpl(DBFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    @Transactional
    @Override
    public DBFile storeFile(MultipartFile file, Task task) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public DBFile getFileById(Long fileId) throws FileNotFoundException {

        Optional<DBFile> fileOptional = dbFileRepository.findById(fileId);
        if (fileOptional.isPresent()) {
            return fileOptional.get();
        } else {
            throw new FileNotFoundException("File not found with ID: " + fileId);
        }
    }
}
