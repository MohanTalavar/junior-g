package com.app.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseWithStudentsRequestDto {
	
	private String title;	
	private LocalDate startDate;	
	private LocalDate endDate;
	private double fees;
	private int capacity;	
	private List<StudentRequestResponseDto> students = new ArrayList<StudentRequestResponseDto>();
	
}
