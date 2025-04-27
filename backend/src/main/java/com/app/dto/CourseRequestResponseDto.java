package com.app.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.app.pojos.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseRequestResponseDto {

	@NotBlank(message = "Title cannot be blank")
	@Size(max = 30, message = "Title must not exceed 30 characters")
	private String title;
	
	@NotNull(message = "Start date cannot be null")
	private LocalDate startDate;
	
	@NotNull(message = "End date cannot be null")
	private LocalDate endDate;
	
	// Use @Min to ensure fees is not negative (or choose @Positive if it must be >0)
    @Min(value = 0, message = "Fees must be at least 0")
	private double fees;
	
    // Use @Min to ensure capacity meets a minimum requirement (e.g., at least 1)
    @Min(value = 1, message = "Capacity must be at least 1")
	private int capacity;

	public CourseRequestResponseDto(Course course) {
		this.title = course.getTitle();
		this.startDate = course.getStartDate();
		this.endDate = course.getEndDate();
		this.fees = course.getFees();
		this.capacity = course.getCapacity();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public double getFees() {
		return fees;
	}

	public void setFees(double fees) {
		this.fees = fees;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}
