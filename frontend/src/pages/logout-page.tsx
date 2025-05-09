import { AppDispatch } from "@/app/store";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "@/features/auth/authSlice";

const Logout: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();

  useEffect(() => {
    // Clear token and user data
    localStorage.removeItem("token");
    localStorage.removeItem("userName");

    // Dispatch logout action to update Redux store
    dispatch(logout());

    // Redirect after a brief delay
    const timer = setTimeout(() => {
      navigate("/", { replace: true });
    }, 2000); // shorter delay for better UX

    // Cleanup
    return () => clearTimeout(timer);
  }, [dispatch, navigate]);

  return (
    <div style={{ textAlign: "center", marginTop: "60px" }}>
      <h2>You’ve been logged out.</h2>
      <p>Redirecting to the home page...</p>
    </div>
  );
};

export default Logout;
