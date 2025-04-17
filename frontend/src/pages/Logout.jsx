import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const Logout = () => {
  const navigate = useNavigate();
  const [showMessage, setShowMessage] = useState(false);

  useEffect(() => {
    // Clear token and user data
    localStorage.removeItem("token");
    localStorage.removeItem("userName");

    // Show logout message
    setShowMessage(true);

    // Redirect after 3 seconds
    const timer = setTimeout(() => {
      navigate("/login");
    }, 3000);

    // Cleanup
    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div style={{ textAlign: "center", marginTop: "20px" }}>
      {showMessage && <h2>You have logged out successfully.</h2>}
      <p>Redirecting to login page...</p>
    </div>
  );
};

export default Logout;
