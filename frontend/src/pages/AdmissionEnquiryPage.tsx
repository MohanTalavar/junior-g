import React, { useState } from "react";
import { Button } from "../components/ui/button";
import { useForm } from "react-hook-form";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { submitEnquiry } from "../api/EnquiryApis"; // Adjust path as per your project
import { toast } from "sonner";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../../src/components/ui/select";

const schema = yup
  .object({
    studentName: yup
      .string()
      .required("Student name is required")
      .max(40, "Student name cannot exceed 40 characters"),
    parentName: yup
      .string()
      .required("Parent name is required")
      .max(40, "Parent name cannot exceed 40 characters"),
    emailId: yup
      .string()
      .email("Invalid email format")
      .required("Email is required"),
    contactNo: yup
      .string()
      .matches(/^\d{10}$/, "Phone number must be a valid 10-digit number")
      .required("Phone number is required"),
    courseName: yup.string().required("Course name is required"),
  })
  .required();

type EnquiryFormData = {
  studentName: string;
  parentName: string;
  emailId: string;
  contactNo: string;
  courseName: string;
};

const AdmissionEnquiryPage: React.FC = () => {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
    setValue,
  } = useForm<EnquiryFormData>({
    resolver: yupResolver(schema),
  });

  const [isSubmitting, setIsSubmitting] = useState(false);

  const onSubmit = async (data: EnquiryFormData) => {
    setIsSubmitting(true);
    try {
      const result = await submitEnquiry(data);
      console.log("API response:", result);
      toast.success("Enquiry submitted successfully!");
      reset(); // clear form
    } catch (error: any) {
      console.error("Enquiry submission failed:", error);
      const backendError =
        error?.response?.data?.error ||
        "Something went wrong. Please try again.";
      toast.error(`Submission failed: ${backendError}`);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="max-w-lg mx-auto p-4">
      <h2 className="text-2xl font-semibold text-center mb-6">
        Admission Enquiry FY 25-26
      </h2>

      <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
        <div>
          <label htmlFor="studentName" className="block text-sm font-medium">
            Student Name
          </label>
          <input
            id="studentName"
            type="text"
            {...register("studentName")}
            className="w-full mt-1 p-2 border rounded-md focus:ring-2 focus:ring-blue-500"
          />
          {errors.studentName && (
            <p className="text-red-500 text-sm">{errors.studentName.message}</p>
          )}
        </div>

        <div>
          <label htmlFor="parentName" className="block text-sm font-medium">
            Parent Name
          </label>
          <input
            id="parentName"
            type="text"
            {...register("parentName")}
            className="w-full mt-1 p-2 border rounded-md focus:ring-2 focus:ring-blue-500"
          />
          {errors.parentName && (
            <p className="text-red-500 text-sm">{errors.parentName.message}</p>
          )}
        </div>

        <div>
          <label htmlFor="emailId" className="block text-sm font-medium">
            Email
          </label>
          <input
            id="emailId"
            type="email"
            {...register("emailId")}
            className="w-full mt-1 p-2 border rounded-md focus:ring-2 focus:ring-blue-500"
          />
          {errors.emailId && (
            <p className="text-red-500 text-sm">{errors.emailId.message}</p>
          )}
        </div>

        <div>
          <label htmlFor="contactNo" className="block text-sm font-medium">
            Contact Number
          </label>
          <input
            id="contactNo"
            type="text"
            {...register("contactNo")}
            className="w-full mt-1 p-2 border rounded-md focus:ring-2 focus:ring-blue-500"
          />
          {errors.contactNo && (
            <p className="text-red-500 text-sm">{errors.contactNo.message}</p>
          )}
        </div>

        <div>
          <label htmlFor="courseName" className="block text-sm font-medium">
            Course Name
          </label>
          <Select
            onValueChange={(value) => setValue("courseName", value)}
            defaultValue=""
          >
            <SelectTrigger className="w-full">
              <SelectValue placeholder="Select a course" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="Daycare">Daycare</SelectItem>
              <SelectItem value="Play Group">Play Group</SelectItem>
              <SelectItem value="Nursery">Nursery</SelectItem>
              <SelectItem value="LKG">LKG</SelectItem>
              <SelectItem value="UKG">UKG</SelectItem>
              <SelectItem value="Summer Camp">Summer Camp</SelectItem>
            </SelectContent>
          </Select>
          {errors.courseName && (
            <p className="text-red-500 text-sm">{errors.courseName.message}</p>
          )}
        </div>

        <div className="mt-4 text-center">
          <Button
            type="submit"
            disabled={isSubmitting}
            className="w-full py-2 bg-blue-600 text-white rounded-md"
          >
            {isSubmitting ? "Submitting..." : "Submit Enquiry"}
          </Button>
        </div>
      </form>
    </div>
  );
};

export default AdmissionEnquiryPage;
