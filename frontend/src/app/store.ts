import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../features/auth/authSlice";

//Define the root state type for your store 
export type RootState = ReturnType<typeof store.getState>;

//Define AppDispatch type for use in dispatcing actions
export type AppDispatch = typeof store.dispatch;

export const store = configureStore({
  reducer: {
    auth: authReducer,
  },
});
