// src/components/login/ResetPasswordForm.tsx
import { useState, ChangeEvent, FormEvent, useEffect } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { resetPassword } from "@/features/auth/authAPI";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";

export function ResetPasswordForm() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const [newPassword, setNewPassword] = useState("");
  const [message, setMessage] = useState("");
  const [token, setToken] = useState<string | null>(null);
  const [error, setError] = useState("");

  useEffect(() => {
    const tokenFromUrl = searchParams.get("token");
    if (tokenFromUrl) {
      setToken(tokenFromUrl);
    } else {
      setError("Invalid or missing token.");
    }
  }, [searchParams]);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    if (!token) {
      setError("Token not found in URL.");
      return;
    }

    try {
      const res = await resetPassword({ token, newPassword });
      setMessage(res); // Backend returns a success string
      setError("");
      setTimeout(() => navigate("/login"), 2000); // Redirect after short delay
    } catch (err) {
      setError("Failed to reset password. Token may be invalid or expired.");
      setMessage("");
    }
  };

  return (
    <div className="min-h-screen bg-white flex items-start justify-center pt-24 px-4">
      <Card className="w-full max-w-[400px] shadow-xl border border-gray-200">
        <CardHeader>
          <CardTitle className="text-2xl text-center text-[#990000] font-semibold">
            Reset Password
          </CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="space-y-2">
              <Label htmlFor="newPassword" className="text-base">
                New Password
              </Label>
              <Input
                id="newPassword"
                type="password"
                value={newPassword}
                onChange={(e: ChangeEvent<HTMLInputElement>) =>
                  setNewPassword(e.target.value)
                }
                required
              />
            </div>

            <Button
              type="submit"
              className="w-full bg-[#990000] hover:bg-red-800 text-white hover:cursor-pointer"
            >
              Reset Password
            </Button>

            {message && (
              <p className="text-sm text-green-600 text-center">{message}</p>
            )}
            {error && (
              <p className="text-sm text-red-600 text-center">{error}</p>
            )}
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
