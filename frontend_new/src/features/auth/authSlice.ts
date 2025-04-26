import { createSlice, PayloadAction } from "@reduxjs/toolkit";

// Define the type of your auth state
type AuthState = {
  user: string | null;
  token: string | null;
  isAuthenticated: boolean;
};

const token = localStorage.getItem("token");

const initialState : AuthState = {
  user: null,
  token: token || null,
  isAuthenticated: !!token,
};

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    setUser:(state, action : PayloadAction<{user : string ; token:string}>) => {
      state.user = action.payload.user;
      state.token = action.payload.token;
      state.isAuthenticated = true;
    },
    logout:(state) => {
      state.user = null;
      state.token = null;
      state.isAuthenticated = false;
      localStorage.removeItem("token");
    },
  },
});

export const { setUser, logout } = authSlice.actions;
export default authSlice.reducer;
