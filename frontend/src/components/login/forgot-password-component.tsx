import { useState, ChangeEvent, FormEvent } from "react";
import { sendPasswordResetRequest } from "@/features/auth/authAPI"; // You'll define this API function
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";

export function ForgotPasswordForm() {
  const [formData, setFormData] = useState({ userName: "", email: "" });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await sendPasswordResetRequest(formData);
      setMessage(
        "If the email matches our records, a reset link will be sent."
      );
      setError("");
    } catch (err) {
      setError("Something went wrong. Please try again.");
      setMessage("");
    }
  };

  return (
    <Card className="w-full max-w-md shadow-xl border border-gray-200">
      <CardHeader>
        <CardTitle className="text-2xl text-[#990000] text-center font-semibold">
          Forgot Password
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
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="email" className="text-base">
              Email
            </Label>
            <Input
              id="email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <Button
            type="submit"
            className="w-full bg-[#990000] hover:bg-red-800 text-white hover:cursor-pointer"
          >
            Send Reset Link
          </Button>

          {message && (
            <p className="text-sm text-green-700 text-center">{message}</p>
          )}
          {error && <p className="text-sm text-red-600 text-center">{error}</p>}
        </form>
      </CardContent>
    </Card>
  );
}
