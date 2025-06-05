import api from "../../api/Junior_G_Apis";

type LoginCredentials = {
    userName: string;
    password: string;
}

type LoginResponse = {
    userName:string;
    role : string;
    token: string;
}

type ForgotPassword = {
    userName: string;
    email: string;
}

type ResetPassword = {
    token: string;
    newPassword: string;
}

export const loginUser = async (credentials:LoginCredentials) =>{
    const response =  await api.post<LoginResponse>("/users/login",credentials);
    return response.data;
};

export async function sendPasswordResetRequest(forgotPassword: ForgotPassword) {
  const response = await api.post("/users/forgot-password", forgotPassword); 
  return response.data;
}

export const resetPassword = async(resetPassword : ResetPassword) =>{
    const response = await api.post("/users/reset-password",resetPassword);
    return response.data;
}