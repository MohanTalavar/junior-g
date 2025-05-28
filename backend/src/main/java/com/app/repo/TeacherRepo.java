package com.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Teacher;
import org.springframework.data.jpa.repository.Query;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
	
	Optional<Teacher> findByEmail(String email);

	// add a method to get the list of teachers with course details
	@Query("SELECT DISTINCT t FROM Teacher t LEFT JOIN FETCH t.courses")
	List<Teacher> findAllWithCourses();

}
