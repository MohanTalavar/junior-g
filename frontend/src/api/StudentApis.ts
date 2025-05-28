// src/api/StudentApis.ts
import api from "./Junior_G_Apis";

/** 
 * Matches your backend’s StudentRequestResponseDto 
 */
export type Student = {
  firstName: string;
  middleName?: string;    // optional, since backend allows blank
  surname: string;
  email: string;
  rollNumber: string;
  phoneNumber: string;
  dateOfBirth: string;     // LocalDate as ISO string
  gender: "Male" | "Female" | "Other";
  fatherName: string;
  motherName: string;
  emergencyContact: string;
  admissionDate: string;   // LocalDate as ISO string
  bloodGroup: string;      // e.g. "A+", "O-"
};

/** 
 * Matches your backend’s CourseWithStudentsResponseDto 
 */
export type CourseWithStudents = {
  courseName: string;
  students: Student[];
};

/**
 * Fetch a course and its enrolled students by courseName
 * GET /teachers/get-course-and-student-details-join-fetch/{courseName}
 */
export const getCourseWithStudents = async (
  courseName: string
): Promise<CourseWithStudents> => {
  const resp = await api.get<CourseWithStudents>(
    `/courses/get-course-and-student-details-join-fetch/${courseName}`
  );
  return resp.data;
};
