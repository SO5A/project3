package com.andrz.project3.repository;

import com.andrz.project3.entity.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DBFileRepository extends JpaRepository<DBFile, String> {
    Optional<DBFile> findById(Long fileId);
}