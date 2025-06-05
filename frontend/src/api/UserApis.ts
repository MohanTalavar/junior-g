import api from "./Junior_G_Apis";

export type User = {
    userName: string;
    email: string;
    role: string;
}

export type NewUser = {
    userName: string;
    email: string;
    password: string;
    role: string;
}

// For updates, we allow email, role, and optionally password
export type UpdateUserType = {
  email: string;
  role: string;
  password?: string;
};

export const getListOfusers = async () : Promise<User[]> => {
    const response = await api.get<User[]>("/users/list");
    return response.data
}

export const addNewUser = async(newUser: NewUser) : Promise<string> =>{
    const resp = await api.post<string>("/users/add-new-user", newUser);
    return resp.data;
}

export const deleteUser = async(userName: string): Promise<String> =>{
    const resp = await api.delete<string>(`/users/delete/${userName}`);
    return resp.data;
}

export const updateUserByUserName = async(
  userName: string,
  updatedUser: UpdateUserType

): Promise<User> =>{
  const resp = await api.put<User>(`/users/update/${userName}`,
    updatedUser
  );
  return resp.data;
};

export const getUserByUserName = async (userName:string) : Promise<User> =>{
    const resp = await api.get<User>(`/users/${userName}`);
    return resp.data;
};
