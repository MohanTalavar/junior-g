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

export const addNewStudent = async(courseName: string, newStudent: Student) : Promise<Student> =>{
  const resp = await api.post<Student>(`/students/admit-new-student-to-course/${courseName}`, newStudent);
  return resp.data;
}

export const deleteStudentByRollNo =  async (courseName:string, studRollNo:string) : Promise<string> =>{
    const resp = await api.delete<string>(`/students/student-admission-cancel/${courseName}/${studRollNo}`);
    return resp.data;
}

export const updateStudentByRollNo = async(
  studentRollNo: string,
  updatedStud: Student

): Promise<Student> =>{
  const resp = await api.put<Student>(`/students/update-student-details/${studentRollNo}`, updatedStud);
  return resp.data;
};

export const getStudentByRollNo = async (studRollNo:string) : Promise<Student> =>{
    const resp = await api.get<Student>(`/students/get-student-details/${studRollNo}`);
    return resp.data;
};
