// src/components/admin/TeacherAdd.tsx
import React from "react";
import { useNavigate } from "react-router-dom";
import { useForm, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { addNewTeacher, NewTeacher } from "@/api/TeacherApis";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { toast } from "sonner";

// 1. Extend schema to include courseName
const newTeacherSchema = z.object({
  courseName: z.enum(["Daycare", "Play Group", "Nursery", "LKG", "UKG"], {
    required_error: "Course is required",
  }),
  firstName: z.string().min(1, "Required"),
  lastName: z.string().min(1, "Required"),
  email: z.string().email("Invalid email"),
  phoneNumber: z.string().min(10, "Must be at least 10 digits"),
  qualification: z.string().min(1, "Required"),
  dateOfJoining: z.string().min(1, "Required"),
});

type FormData = z.infer<typeof newTeacherSchema>;

const TeacherAdd: React.FC = () => {
  const navigate = useNavigate();

  const {
    control,
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
  } = useForm<FormData>({
    resolver: zodResolver(newTeacherSchema),
    defaultValues: { courseName: "Daycare" },
  });

  const onSubmit = async (data: FormData) => {
    try {
      // Pass courseName separately, the rest as Teacher payload (minus id)
      await addNewTeacher(data.courseName, data as NewTeacher);
      toast.success("Teacher hired successfully");
      navigate("/teacher");
    } catch {
      toast.error("Failed to hire teacher.");
    }
  };

  return (
    <Card className="max-w-xl mx-auto mt-8">
      <CardHeader>
        <CardTitle className="font-bold text-2xl">Hire New Teacher</CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          {/* Course Dropdown */}
          <div>
            <Label htmlFor="courseName">Course</Label>
            <Controller
              name="courseName"
              control={control}
              render={({ field }) => (
                <Select onValueChange={field.onChange} value={field.value}>
                  <SelectTrigger className="w-full">
                    <SelectValue placeholder="Select a course" />
                  </SelectTrigger>
                  <SelectContent className="bg-white">
                    <SelectGroup>
                      {["Daycare", "Play Group", "Nursery", "LKG", "UKG"].map(
                        (course) => (
                          <SelectItem key={course} value={course}>
                            {course}
                          </SelectItem>
                        )
                      )}
                    </SelectGroup>
                  </SelectContent>
                </Select>
              )}
            />
            {errors.courseName && (
              <p className="text-red-500 text-sm">
                {errors.courseName.message}
              </p>
            )}
          </div>

          {/* Existing Fields */}
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

          {/* Form Actions */}
          <div className="flex justify-end space-x-2">
            <Button
              className="bg-red-600 hover:bg-red-800 hover:cursor-pointer text-white"
              variant="outline"
              onClick={() => navigate("/teacher")}
            >
              Cancel
            </Button>
            <Button
              className="bg-green-600 hover:bg-green-800 text-white hover:cursor-pointer"
              variant={"outline"}
              type="submit"
              disabled={isSubmitting}
            >
              Hire
            </Button>
          </div>
        </form>
      </CardContent>
    </Card>
  );
};

export default TeacherAdd;
