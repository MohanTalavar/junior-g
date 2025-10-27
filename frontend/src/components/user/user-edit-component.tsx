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

// Zod schema: now includes editable username
const userEditSchema = z.object({
  userName: z.string().min(3, "Username must be at least 3 characters"),
  email: z.string().email("Invalid email address"),
  role: z.enum(["ADMIN", "TEACHER"]),
});

type FormData = z.infer<typeof userEditSchema>;
const ROLES = ["ADMIN", "TEACHER"] as const;

export function UserEditComponent() {
  const { userName: originalUserName = "" } = useParams<{ userName: string }>();
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
      userName: "",
      email: "",
      role: "ADMIN",
    },
  });

  useEffect(() => {
    if (!isAuthenticated || !ALLOWED_ROLES.includes(currentRole || "")) return;

    setLoading(true);
    getUserByUserName(originalUserName)
      .then((u: User) => {
        reset({
          userName: u.userName,
          email: u.email,
          role: u.role.replace("ROLE_", "").toLowerCase() as
            | "ADMIN"
            | "TEACHER",
        });
      })
      .catch(() => {
        toast.error("Failed to load user details.");
      })
      .finally(() => setLoading(false));
  }, [originalUserName, currentRole, isAuthenticated, reset]);

  const onSubmit = async (data: FormData) => {
    try {
      const payload: UpdateUserType = {
        userName: data.userName,
        email: data.email,
        role: data.role,
      };

      await updateUserByUserName(originalUserName, payload);
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
            Edit User — {originalUserName}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <div className="text-center py-10">Loading user details...</div>
          ) : (
            <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
              <div>
                <Label htmlFor="userName">Username</Label>
                <Input id="userName" type="text" {...register("userName")} />
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
                <Label htmlFor="role">Role</Label>
                <Controller
                  name="role"
                  control={control}
                  render={({ field }) => (
                    <Select onValueChange={field.onChange} value={field.value}>
                      <SelectTrigger id="role" className="w-full">
                        <SelectValue placeholder="Select role" />
                      </SelectTrigger>
                      <SelectContent className="bg-white">
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

              <div className="flex justify-end space-x-2 pt-4">
                <Button
                  type="button"
                  className="bg-red-600 hover:bg-red-800 hover:cursor-pointer text-white"
                  variant="outline"
                  onClick={() => navigate("/admin/users")}
                  disabled={isSubmitting}
                >
                  Cancel
                </Button>
                <Button
                  className="bg-blue-600 hover:bg-blue-800 hover:cursor-pointer text-white"
                  type="submit"
                  disabled={isSubmitting}
                >
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
