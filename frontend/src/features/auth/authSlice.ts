import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// Define the type of your auth state
type AuthState = {
  user: string | null;
  role: string | null;
  token: string | null;
  isAuthenticated: boolean;
};

const token = localStorage.getItem("token");
const role = localStorage.getItem("role");
const user  = localStorage.getItem("user");

const initialState : AuthState = {
  user: user || null,
  role : role || null,
  token: token || null,
  isAuthenticated: !!token,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setUser:(state, 
      action : PayloadAction<{user : string ; role : string ; token:string}>) => {
      state.user = action.payload.user;
      state.token = action.payload.token;
      state.role = action.payload.role;
      state.isAuthenticated = true;
      localStorage.setItem("user",  action.payload.user);
      localStorage.setItem("token", action.payload.token);
      localStorage.setItem("role", action.payload.role);
    },
    logout:(state) => {
      state.user = null;
      state.token = null;
      state.role = null;
      state.isAuthenticated = false;
      localStorage.removeItem("user");
      localStorage.removeItem("token");
      localStorage.removeItem("role");
    },
  },
});

export const { setUser, logout } = authSlice.actions;
export default authSlice.reducer;
