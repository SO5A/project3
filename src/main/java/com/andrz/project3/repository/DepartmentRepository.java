package com.andrz.project3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrz.project3.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
