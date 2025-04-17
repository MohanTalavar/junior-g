import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // Your backend base URL
});

// Automatically attach token to every request, except for the login endpoint
api.interceptors.request.use((config) => {
  // Skip adding token for login request
  if (config.url !== "/users/login" && config.url !== "/login") {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

// Global error handler for expired or invalid token
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // If the status is 401 (Unauthorized), the token might be expired or invalid
    if (error.response && error.response.status === 401) {
      console.warn("Unauthorized! Possibly due to expired token.");
      localStorage.removeItem("token"); // Clear token from localStorage
      localStorage.removeItem("userName"); // Optionally remove username as well
      window.location.href = "/login"; // Redirect to login page
    }
    return Promise.reject(error);
  }
);

export default api;
