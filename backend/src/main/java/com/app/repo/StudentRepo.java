package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Student;

import java.util.Optional;

// Make sure to put the same data type as of the base entity i.e Long
public interface StudentRepo extends JpaRepository<Student, Long>{

    Optional<Student> findByRollNumber(String rollNumber);
}
