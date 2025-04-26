import api from "../../api/Junior_G_Apis";

type LoginCredentials = {
    userName: string;
    password: string;
}

type LoginResponse = {
    userName:string;
    token: string;
}

export const loginUser = async (credentials:LoginCredentials) =>{
    const response =  await api.post<LoginResponse>("/users/login",credentials);
    return response.data;
};