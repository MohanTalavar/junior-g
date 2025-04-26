import { ChangeEvent, useState, FormEvent } from "react";
import { useDispatch } from "react-redux";
import { setUser } from "../features/auth/authSlice";
import { loginUser } from "../features/auth/authAPI";
import { useNavigate } from "react-router-dom";
import { AppDispatch } from "@/app/store";
import { Button } from "@/components/ui/button";

type LoginFormData = {
  userName: string;
  password: string;
};

const Login = () => {
  const dispatch = useDispatch<AppDispatch>();
  const [formData, setFormData] = useState<LoginFormData>({
    userName: "",
    password: "",
  });
  const [error, setError] = useState<string>("");
  const navigate = useNavigate();

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const data = await loginUser(formData);
      localStorage.setItem("token", data.token);
      dispatch(setUser({ user: data.userName, token: data.token }));
      setError("");
      navigate("/home"); // 👈 Redirect after login
    } catch (err) {
      setError("Invalid credentials. Please try again.");
    }
  };

  return (
    <div className="login-container flex p-6 items-center h-screen flex-col">
      <h2 className="text-3xl p-6 text-orange-500">Login to Junior G</h2>
      <form onSubmit={handleSubmit}>
        <input
          className="border-2"
          type="text"
          name="userName"
          placeholder="Username"
          value={formData.userName}
          onChange={handleChange}
          required
        />
        <br />
        <input
          className="mt-2 border-2"
          type="password"
          name="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <br />
        {/* <button type="submit">Login</button> */}
        <Button className="bg-yellow-600 mt-3" type="submit">
          Login by ShadCN
        </Button>
      </form>
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
};

export default Login;
