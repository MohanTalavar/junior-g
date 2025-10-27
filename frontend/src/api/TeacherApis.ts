import api from "./Junior_G_Apis";

export type Teacher = {
  id : number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  qualification: string;
  dateOfJoining: string; // LocalDate from backend, which we can handle as string in TS
  // courses: string[];
};

export type TeacherWithCourse = {
  id : number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  qualification: string;
  dateOfJoining: string; // LocalDate from backend, which we can handle as string in TS
  courses: string[];
};

export type NewTeacher = Omit<Teacher,"id">;

export const getTeacherById = async (teacherId:number) : Promise<Teacher> =>{
    const resp = await api.get<Teacher>(`/teachers/get-teacher-details/${teacherId}`);
    return resp.data;
};

// As the teacher contains the id field. But in BE we create it autmatically so we omit the field "id"
export const addNewTeacher = async(courseName: string ,newTeacher : NewTeacher) : Promise<string> =>{
  const resp = await api.post<string>(`/teachers/assign-new-teacher-to-course/${courseName}`, newTeacher);
  return resp.data;
}

export const deleteTeacherById =  async (teacherId:number) : Promise<string> =>{
    const resp = await api.delete<string>(`/teachers/delete-teacher-details/${teacherId}`);
    return resp.data;
}

export const getTeacherList = async (): Promise<TeacherWithCourse[]> =>{
  const resp = await api.get<TeacherWithCourse[]>("/teachers/teacher-list");
  return resp.data;
}

export const updateTeacher = async(
  teacherId: number,
  updatedTeacher: Teacher

): Promise<Teacher> =>{
  const resp = await api.put<Teacher>(`/teachers/update-teacher-details/${teacherId}`,
    updatedTeacher
  );
  return resp.data;
};