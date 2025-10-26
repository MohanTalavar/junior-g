package com.juniorg.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.juniorg.pojos.Course;

import java.util.Optional;

//Make sure to put the same data type as of the base entity i.e Long
public interface CourseRepo extends JpaRepository<Course, Long> {

	Optional<Course> findByTitle(String courseName);

	void deleteByTitle(String courseToBeRemoved);
	
	// If you encounter NoResultExp meaning there are no student enrolled
	// You can handle this by adding left outer join
	@Query("SELECT c FROM Course c JOIN FETCH c.students WHERE c.title = :title")
	Optional<Course> findCourseWithStudentsByTitle(@Param("title") String title);

}
