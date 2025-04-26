import api from "./Junior_G_Apis";

export type Teacher = {
firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  qualification: string;
  dateOfJoining: string; // LocalDate from backend, which we can handle as string in TS
};

export const getTeacherById = async (teacherId:number) : Promise<Teacher> =>{
    const resp = await api.get<Teacher>(`/teachers/get-teacher-details/${teacherId}`);
    return resp.data;
};