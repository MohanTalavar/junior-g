import api from "../../api/Junior_G_Apis";

export const loginUser = async (credentials) =>{
    const response =  await api.post("/users/login",credentials);
    return response.data;
};