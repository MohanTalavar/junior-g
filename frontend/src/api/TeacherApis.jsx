import api from "./Junior_G_Apis";

export const getTeacherById = async (teacherId) =>{
    const resp = await api.get(`/teachers/get-teacher-details/${teacherId}`);
    return resp.data;
};