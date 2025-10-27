// src/hooks/useAuth.ts
import { useSelector } from "react-redux";
import { RootState } from "../app/store"; // adjust if your store path is different

export const useAuth = () => {
  const { user, role, isAuthenticated } = useSelector(
    (state: RootState) => state.auth
  );

  return { user, role, isAuthenticated };
};
