package com.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Teacher;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {
	
	Optional<Teacher> findByEmail(String email);

}
