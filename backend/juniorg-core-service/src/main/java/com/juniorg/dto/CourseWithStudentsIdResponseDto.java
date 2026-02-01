package com.juniorg.dto;

import com.juniorg.pojos.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseWithStudentsIdResponseDto {

    private String courseName;
    private List<StudentWithIdDto> students = new ArrayList<>();

    public CourseWithStudentsIdResponseDto(Course course) {
        this.courseName = course.getTitle();
        this.students = course.getStudents().stream().map(StudentWithIdDto::new).toList();
    }
}
