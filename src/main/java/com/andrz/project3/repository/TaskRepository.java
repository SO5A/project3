package com.andrz.project3.repository;

import java.util.List;

import com.andrz.project3.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {

	@Query("SELECT b FROM Task b WHERE b.name LIKE %?1%")
	public List<Task> search(String keyword);

	Object findByTitleContaining(String keyword);
}
