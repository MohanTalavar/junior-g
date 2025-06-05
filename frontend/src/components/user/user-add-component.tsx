import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { addNewUser } from "@/api/UserApis";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

// Zod validation schema
const userSchema = z.object({
  userName: z.string().min(2, "Username is required"),
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
  role: z
    .enum(["ADMIN", "TEACHER"])
    .refine((val) => ["ADMIN", "TEACHER"].includes(val), {
      message: "Role must be ADMIN or TEACHER",
    }),
});

type UserFormData = z.infer<typeof userSchema>;

export function UserAddComponent() {
  const {
    register,
    handleSubmit,
    setValue,
    reset,
    formState: { errors },
  } = useForm<UserFormData>({
    resolver: zodResolver(userSchema),
    defaultValues: {
      role: "ADMIN",
    },
  });

  const [successMsg, setSuccessMsg] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const navigate = useNavigate();

  const onSubmit = async (formData: UserFormData) => {
    setSuccessMsg("");
    setErrorMsg("");

    try {
      const msg = await addNewUser(formData);
      setSuccessMsg(msg);
      reset({ userName: "", email: "", password: "", role: "ADMIN" });

      // Navigate to /admin/users after successful add
      navigate("/admin/users");
    } catch (err) {
      setErrorMsg("Failed to add user. Please check input or permissions.");
    }
  };

  return (
    <div className="flex justify-center items-center min-h-screen">
      <Card className="w-full max-w-lg shadow-lg border border-gray-200">
        <CardHeader>
          <CardTitle className="text-xl text-center text-[#990000] font-semibold">
            Add New User
          </CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
            <div>
              <Label htmlFor="userName">Username</Label>
              <Input id="userName" {...register("userName")} />
              {errors.userName && (
                <p className="text-red-600 text-sm">
                  {errors.userName.message}
                </p>
              )}
            </div>

            <div>
              <Label htmlFor="email">Email</Label>
              <Input id="email" type="email" {...register("email")} />
              {errors.email && (
                <p className="text-red-600 text-sm">{errors.email.message}</p>
              )}
            </div>

            <div>
              <Label htmlFor="password">Password</Label>
              <Input id="password" type="password" {...register("password")} />
              {errors.password && (
                <p className="text-red-600 text-sm">
                  {errors.password.message}
                </p>
              )}
            </div>

            <div>
              <Label htmlFor="role">Role</Label>
              <Select
                defaultValue="admin"
                onValueChange={(value) =>
                  setValue("role", value as "ADMIN" | "TEACHER")
                }
              >
                <SelectTrigger id="role">
                  <SelectValue placeholder="Select role" />
                </SelectTrigger>
                <SelectContent className="bg-white">
                  <SelectItem value="ADMIN">Admin</SelectItem>
                  <SelectItem value="TEACHER">Teacher</SelectItem>
                </SelectContent>
              </Select>
              {errors.role && (
                <p className="text-red-600 text-sm">{errors.role.message}</p>
              )}
            </div>

            <Button
              type="submit"
              className="w-full bg-[#990000] hover:bg-red-800 text-white hover:cursor-pointer"
            >
              Add User
            </Button>

            {successMsg && (
              <p className="text-green-600 text-center">{successMsg}</p>
            )}
            {errorMsg && <p className="text-red-600 text-center">{errorMsg}</p>}
          </form>
        </CardContent>
      </Card>
    </div>
  );
}
