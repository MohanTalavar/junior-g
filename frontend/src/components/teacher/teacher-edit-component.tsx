// src/components/admin/TeacherEdit.tsx
import React, { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getTeacherById, updateTeacher, Teacher } from "@/api/TeacherApis";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { toast } from "sonner";

// Zod schema for validation
const teacherSchema = z.object({
  firstName: z.string().min(1, "Required"),
  lastName: z.string().min(1, "Required"),
  email: z.string().email("Invalid email"),
  phoneNumber: z.string().min(10, "Must be at least 10 digits"),
  qualification: z.string().min(1, "Required"),
  dateOfJoining: z.string().min(1, "Required"),
});

type FormData = z.infer<typeof teacherSchema>;

const TeacherEdit: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<FormData>({ resolver: zodResolver(teacherSchema) });

  useEffect(() => {
    if (!id) return;
    getTeacherById(Number(id))
      .then((data: Teacher) => {
        // Populate form
        reset({
          firstName: data.firstName,
          lastName: data.lastName,
          email: data.email,
          phoneNumber: data.phoneNumber,
          qualification: data.qualification,
          dateOfJoining: data.dateOfJoining,
        });
      })
      .catch(() => {
        toast.error("Failed to load teacher details.");
      });
  }, [id, reset]);

  const onSubmit = async (values: FormData) => {
    try {
      await updateTeacher(Number(id), values as Teacher);
      toast.success("Teacher updated successfully");
      navigate("/teacher");
    } catch {
      toast.error("Failed to update teacher.");
    }
  };

  return (
    <Card className="max-w-xl mx-auto mt-8 ">
      <CardHeader>
        <CardTitle>Edit Teacher</CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <Label htmlFor="firstName">First Name</Label>
            <Input id="firstName" {...register("firstName")} />
            {errors.firstName && (
              <p className="text-red-500 text-sm">{errors.firstName.message}</p>
            )}
          </div>
          <div>
            <Label htmlFor="lastName">Last Name</Label>
            <Input id="lastName" {...register("lastName")} />
            {errors.lastName && (
              <p className="text-red-500 text-sm">{errors.lastName.message}</p>
            )}
          </div>
          <div>
            <Label htmlFor="email">Email</Label>
            <Input type="email" id="email" {...register("email")} />
            {errors.email && (
              <p className="text-red-500 text-sm">{errors.email.message}</p>
            )}
          </div>
          <div>
            <Label htmlFor="phoneNumber">Phone Number</Label>
            <Input id="phoneNumber" {...register("phoneNumber")} />
            {errors.phoneNumber && (
              <p className="text-red-500 text-sm">
                {errors.phoneNumber.message}
              </p>
            )}
          </div>
          <div>
            <Label htmlFor="qualification">Qualification</Label>
            <Input id="qualification" {...register("qualification")} />
            {errors.qualification && (
              <p className="text-red-500 text-sm">
                {errors.qualification.message}
              </p>
            )}
          </div>
          <div>
            <Label htmlFor="dateOfJoining">Date of Joining</Label>
            <Input
              type="date"
              id="dateOfJoining"
              {...register("dateOfJoining")}
            />
            {errors.dateOfJoining && (
              <p className="text-red-500 text-sm">
                {errors.dateOfJoining.message}
              </p>
            )}
          </div>
          <div className="flex justify-end space-x-2">
            <Button
              className="hover:cursor-pointer bg-red-600 hover:bg-red-800 text-white"
              type="button"
              variant="outline"
              onClick={() => navigate("/teacher")}
            >
              Cancel
            </Button>
            <Button
              className="hover:cursor-pointer bg-blue-600 hover:bg-blue-800 text-white"
              variant={"outline"}
              type="submit"
              disabled={isSubmitting}
            >
              Save
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
};

export default TeacherEdit;
