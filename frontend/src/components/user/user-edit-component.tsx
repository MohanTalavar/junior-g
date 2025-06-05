// src/components/admin/UserEditComponent.tsx
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useForm, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";

import {
  getUserByUserName,
  updateUserByUserName,
  User,
  UpdateUserType,
} from "@/api/UserApis";
import { useAuth } from "@/hooks/useAuth";
import { toast } from "sonner";

import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectGroup,
  SelectItem,
} from "@/components/ui/select";

// 1. Zod schema: email/role required; password optional but min 6 if provided
const userEditSchema = z.object({
  email: z.string().email("Invalid email address"),
  role: z.enum(["admin", "teacher"]), // no second argument here
  password: z
    .string()
    .min(6, "Password must be at least 6 characters")
    .optional()
    .or(z.literal("")),
});

type FormData = z.infer<typeof userEditSchema>;
const ROLES = ["admin", "teacher"] as const;

export function UserEditComponent() {
  const { userName = "" } = useParams<{ userName: string }>();
  const navigate = useNavigate();
  const { role: currentRole, isAuthenticated } = useAuth();
  const ALLOWED_ROLES = ["ROLE_ADMIN"];

  const [loading, setLoading] = useState<boolean>(false);

  const {
    register,
    handleSubmit,
    reset,
    control,
    formState: { errors, isSubmitting },
  } = useForm<FormData>({
    resolver: zodResolver(userEditSchema),
    defaultValues: {
      email: "",
      role: "admin",
      password: "",
    },
  });

  // 2. Fetch user on mount
  useEffect(() => {
    if (!isAuthenticated || !ALLOWED_ROLES.includes(currentRole || "")) {
      return;
    }
    setLoading(true);
    getUserByUserName(userName)
      .then((u: User) => {
        reset({
          email: u.email,
          role: u.role.replace("ROLE_", "").toLowerCase() as
            | "admin"
            | "teacher",
          password: "",
        });
      })
      .catch(() => {
        toast.error("Failed to load user details.");
      })
      .finally(() => setLoading(false));
  }, [userName, currentRole, isAuthenticated, reset]);

  // 3. Submit handler
  const onSubmit = async (data: FormData) => {
    try {
      const payload: UpdateUserType = {
        email: data.email,
        role: data.role,
      };
      if (data.password) {
        payload.password = data.password;
      }

      await updateUserByUserName(userName, payload);
      toast.success("User updated successfully");
      navigate("/admin/users");
    } catch {
      toast.error("Failed to update user.");
    }
  };

  if (!isAuthenticated || !ALLOWED_ROLES.includes(currentRole || "")) {
    return (
      <div className="p-4 text-center">
        You are not authorized to view this page.
      </div>
    );
  }

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-50">
      <Card className="w-full max-w-lg shadow-lg border border-gray-200">
        <CardHeader>
          <CardTitle className="text-2xl text-center text-[#990000] font-semibold">
            Edit User — {userName}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <div className="text-center py-10">Loading user details...</div>
          ) : (
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
              <div>
                <Label htmlFor="email">Email</Label>
                <Input id="email" type="email" {...register("email")} />
                {errors.email && (
                  <p className="text-red-600 text-sm">{errors.email.message}</p>
                )}
              </div>

              <div>
                <Label htmlFor="role">Role</Label>
                <Controller
                  name="role"
                  control={control}
                  render={({ field }) => (
                    <Select onValueChange={field.onChange} value={field.value}>
                      <SelectTrigger id="role" className="w-full">
                        <SelectValue placeholder="Select role" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectGroup>
                          {ROLES.map((r) => (
                            <SelectItem key={r} value={r}>
                              {r.charAt(0).toUpperCase() + r.slice(1)}
                            </SelectItem>
                          ))}
                        </SelectGroup>
                      </SelectContent>
                    </Select>
                  )}
                />
                {errors.role && (
                  <p className="text-red-600 text-sm">{errors.role.message}</p>
                )}
              </div>

              <div>
                <Label htmlFor="password">New Password (optional)</Label>
                <Input
                  id="password"
                  type="password"
                  {...register("password")}
                />
                {errors.password && (
                  <p className="text-red-600 text-sm">
                    {errors.password.message}
                  </p>
                )}
              </div>

              <div className="flex justify-end space-x-2 pt-4">
                <Button
                  variant="outline"
                  onClick={() => navigate("/admin/users")}
                  disabled={isSubmitting}
                >
                  Cancel
                </Button>
                <Button type="submit" disabled={isSubmitting}>
                  Save
                </Button>
              </div>
            </form>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
