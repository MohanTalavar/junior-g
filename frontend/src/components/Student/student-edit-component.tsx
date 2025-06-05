// src/components/admin/StudentEdit.tsx
import React, { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useForm, Controller } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import {
  getStudentByRollNo,
  updateStudentByRollNo,
  Student,
} from "@/api/StudentApis";
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
import { toast } from "sonner";

// Zod schema (same as add, minus courseName)
const studentSchema = z.object({
  firstName: z.string().min(1, "Required"),
  middleName: z.string().optional(),
  surname: z.string().min(1, "Required"),
  email: z.string().email("Invalid email"),
  rollNumber: z.string().min(1, "Required"),
  phoneNumber: z.string().regex(/^\d{10}$/, "Must be 10 digits"),
  dateOfBirth: z.string().min(1, "Required"),
  gender: z.enum(["Male", "Female", "Other"], { required_error: "Required" }),
  fatherName: z.string().min(1, "Required"),
  motherName: z.string().min(1, "Required"),
  emergencyContact: z.string().regex(/^\d{10}$/, "Must be 10 digits"),
  admissionDate: z.string().min(1, "Required"),
  bloodGroup: z.enum(["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"], {
    required_error: "Required",
  }),
});

type FormData = z.infer<typeof studentSchema>;

const GENDERS = ["Male", "Female", "Other"] as const;
const BLOOD_GROUPS = [
  "A+",
  "A-",
  "B+",
  "B-",
  "AB+",
  "AB-",
  "O+",
  "O-",
] as const;

const StudentEdit: React.FC = () => {
  const { rollNumber = "" } = useParams<{ rollNumber: string }>();
  const navigate = useNavigate();

  const {
    register,
    control,
    handleSubmit,
    reset,
    formState: { errors, isSubmitting },
  } = useForm<FormData>({
    resolver: zodResolver(studentSchema),
  });

  // Load student on mount
  useEffect(() => {
    if (!rollNumber) return;
    getStudentByRollNo(rollNumber)
      .then((stu: Student) => {
        reset({
          firstName: stu.firstName,
          middleName: stu.middleName,
          surname: stu.surname,
          email: stu.email,
          rollNumber: stu.rollNumber,
          phoneNumber: stu.phoneNumber,
          dateOfBirth: stu.dateOfBirth,
          // Cast to literal types
          gender: stu.gender as FormData["gender"],
          fatherName: stu.fatherName,
          motherName: stu.motherName,
          emergencyContact: stu.emergencyContact,
          admissionDate: stu.admissionDate,
          bloodGroup: stu.bloodGroup as FormData["bloodGroup"],
        });
      })
      .catch(() => {
        toast.error("Failed to load student details.");
      });
  }, [rollNumber, reset]);

  // Submit handler
  const onSubmit = async (data: FormData) => {
    try {
      await updateStudentByRollNo(rollNumber, data as Student);
      toast.success("Student updated successfully");
      navigate("/student");
    } catch {
      toast.error("Failed to update student.");
    }
  };

  return (
    <Card className="max-w-2xl mx-auto mt-8">
      <CardHeader>
        <CardTitle className="text-2xl font-bold">
          Edit Student — {rollNumber}
        </CardTitle>
      </CardHeader>
      <CardContent>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          {/* First/Middle/Last */}
          <div>
            <Label htmlFor="firstName">First Name</Label>
            <Input id="firstName" {...register("firstName")} />
            {errors.firstName && (
              <p className="text-red-500 text-sm">{errors.firstName.message}</p>
            )}
          </div>
          <div>
            <Label htmlFor="middleName">Middle Name</Label>
            <Input id="middleName" {...register("middleName")} />
          </div>
          <div>
            <Label htmlFor="surname">Surname</Label>
            <Input id="surname" {...register("surname")} />
            {errors.surname && (
              <p className="text-red-500 text-sm">{errors.surname.message}</p>
            )}
          </div>

          {/* Contact */}
          <div>
            <Label htmlFor="email">Email</Label>
            <Input id="email" type="email" {...register("email")} />
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

          {/* Dates */}
          <div>
            <Label htmlFor="dateOfBirth">Date of Birth</Label>
            <Input id="dateOfBirth" type="date" {...register("dateOfBirth")} />
            {errors.dateOfBirth && (
              <p className="text-red-500 text-sm">
                {errors.dateOfBirth.message}
              </p>
            )}
          </div>

          {/* Gender */}
          <div>
            <Label htmlFor="gender">Gender</Label>
            <Controller
              name="gender"
              control={control}
              render={({ field }) => (
                <Select onValueChange={field.onChange} value={field.value}>
                  <SelectTrigger className="w-full">
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectGroup>
                      {GENDERS.map((g) => (
                        <SelectItem key={g} value={g}>
                          {g}
                        </SelectItem>
                      ))}
                    </SelectGroup>
                  </SelectContent>
                </Select>
              )}
            />
            {errors.gender && (
              <p className="text-red-500 text-sm">{errors.gender.message}</p>
            )}
          </div>

          {/* Parents & Emergency */}
          <div>
            <Label htmlFor="fatherName">Father’s Name</Label>
            <Input id="fatherName" {...register("fatherName")} />
            {errors.fatherName && (
              <p className="text-red-500 text-sm">
                {errors.fatherName.message}
              </p>
            )}
          </div>
          <div>
            <Label htmlFor="motherName">Mother’s Name</Label>
            <Input id="motherName" {...register("motherName")} />
            {errors.motherName && (
              <p className="text-red-500 text-sm">
                {errors.motherName.message}
              </p>
            )}
          </div>
          <div>
            <Label htmlFor="emergencyContact">Emergency Contact</Label>
            <Input id="emergencyContact" {...register("emergencyContact")} />
            {errors.emergencyContact && (
              <p className="text-red-500 text-sm">
                {errors.emergencyContact.message}
              </p>
            )}
          </div>

          {/* Admission Date */}
          <div>
            <Label htmlFor="admissionDate">Admission Date</Label>
            <Input
              id="admissionDate"
              type="date"
              {...register("admissionDate")}
            />
            {errors.admissionDate && (
              <p className="text-red-500 text-sm">
                {errors.admissionDate.message}
              </p>
            )}
          </div>

          {/* Blood Group */}
          <div>
            <Label htmlFor="bloodGroup">Blood Group</Label>
            <Controller
              name="bloodGroup"
              control={control}
              render={({ field }) => (
                <Select onValueChange={field.onChange} value={field.value}>
                  <SelectTrigger className="w-full">
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent className="bg-white">
                    <SelectGroup>
                      {BLOOD_GROUPS.map((bg) => (
                        <SelectItem key={bg} value={bg}>
                          {bg}
                        </SelectItem>
                      ))}
                    </SelectGroup>
                  </SelectContent>
                </Select>
              )}
            />
            {errors.bloodGroup && (
              <p className="text-red-500 text-sm">
                {errors.bloodGroup.message}
              </p>
            )}
          </div>

          {/* Form Actions */}
          <div className="flex justify-end space-x-2">
            <Button
              className="hover:cursor-pointer bg-red-600 hover:bg-red-800 text-white"
              variant="outline"
              onClick={() => navigate("/admin/students")}
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

export default StudentEdit;
