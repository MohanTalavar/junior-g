import { useState, ChangeEvent, FormEvent } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { loginUser } from "@/features/auth/authAPI";
import { setUser } from "@/features/auth/authSlice";
import { AppDispatch } from "@/app/store";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { toast } from "sonner";

export function LoginForm() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({ userName: "", password: "" });
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false); // ✅ new state for loader

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const data = await loginUser(formData);
      dispatch(
        setUser({
          user: data.userName,
          role: data.role,
          token: data.token,
        })
      );
      setError("");

      // ✅ Success toast
      toast.success(`Welcome back, ${data.userName}!`, {
        description: "You have successfully logged in.",
        duration: 3000,
        position: "top-center",
        className: "bg-white",
      });

      navigate("/");
    } catch (err) {
      setError("Invalid credentials. Please try again.");

      // ❌ Error toast
      toast.error("Login Failed", {
        description: "Invalid username or password. Please try again.",
        duration: 4000,
        position: "top-center",
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card className="w-full max-w-md shadow-xl border border-gray-200">
      <CardHeader>
        <CardTitle className="text-2xl text-[#990000] text-center font-semibold">
          Login to Junior G
        </CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="space-y-2">
            <Label htmlFor="userName" className="text-base">
              Username
            </Label>
            <Input
              id="userName"
              name="userName"
              type="text"
              value={formData.userName}
              onChange={handleChange}
              required
              className="w-full"
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="password" className="text-base">
              Password
            </Label>
            <Input
              id="password"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              required
              className="w-full"
            />
          </div>

          {/* ✅ Login Button with Loader */}
          <Button
            type="submit"
            disabled={loading}
            className="w-full bg-[#990000] hover:bg-red-800 text-white cursor-pointer flex items-center justify-center gap-2"
          >
            {loading ? (
              <>
                <span className="w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin"></span>
                Logging in...
              </>
            ) : (
              "Login"
            )}
          </Button>

          <Link to="/forgot-password" className="text-blue-800 hover:underline">
            Forgot password?
          </Link>

          {error && <p className="text-sm text-red-600 text-center">{error}</p>}
        </form>
      </CardContent>
    </Card>
  );
}
