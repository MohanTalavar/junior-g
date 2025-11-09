// src/components/admin/StudentList.tsx
import React, { useEffect, useState } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";
import {
  AlertDialog,
  AlertDialogTrigger,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogDescription,
  AlertDialogAction,
  AlertDialogCancel,
} from "@/components/ui/alert-dialog";
import { useAuth } from "@/hooks/useAuth";
import {
  getCourseWithStudents,
  CourseWithStudents,
  Student,
  deleteStudentByRollNo,
} from "@/api/StudentApis";
import { toast } from "sonner";
import { cn } from "@/lib/utils";

const COURSES = ["Playgroup", "Nursery", "LKG", "UKG"] as const;

const StudentList: React.FC = () => {
  const { role, isAuthenticated } = useAuth();
  const [selectedCourse, setSelectedCourse] =
    useState<(typeof COURSES)[number]>("Playgroup");
  const [data, setData] = useState<CourseWithStudents | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const [deleteId, setDeleteId] = useState<string | null>(null);
  const navigate = useNavigate();
  const ALLOWED_ROLES = ["ROLE_ADMIN", "ROLE_TEACHER"];

  // 1. Extracted loader so we can call it from both useEffect and handleDelete
  const loadStudents = async () => {
    setLoading(true);
    try {
      const result = await getCourseWithStudents(selectedCourse);
      setData(result);
    } catch (err) {
      console.error(err);
      toast.error(`Failed to load students for ${selectedCourse}`);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (isAuthenticated && ALLOWED_ROLES.includes(role || "")) {
      loadStudents();
    }
  }, [selectedCourse, isAuthenticated, role]);

  if (!isAuthenticated || !ALLOWED_ROLES.includes(role || "")) {
    return <div>You are not authorized to view this page.</div>;
  }

  // 2. Correct delete handler
  const handleDelete = async () => {
    if (!deleteId) return;
    try {
      await deleteStudentByRollNo(selectedCourse, deleteId);
      toast.success("Student deleted successfully");
      setDeleteId(null);
      await loadStudents();
    } catch (err) {
      console.error(err);
      toast.error("Failed to delete student.");
    }
  };

  // helper function for display formatting
  const formatDate = (dateString?: string) => {
    if (!dateString) return "-";
    const date = new Date(dateString);
    if (isNaN(date.getTime())) return dateString; // fallback if invalid
    return date.toLocaleDateString("en-GB", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    });
  };

  return (
    <Card className="space-y-4">
      <CardHeader className="flex flex-col md:flex-row md:items-center md:justify-between space-y-2 md:space-y-0">
        <CardTitle className="text-3xl font-bold text-[#3D348B]">
          Student Management
        </CardTitle>
        <div className="flex flex-wrap gap-2">
          {COURSES.map((course) => {
            const isActive = selectedCourse === course;

            return (
              <Button
                key={course}
                size="sm"
                variant={isActive ? "default" : "outline"}
                onClick={() => setSelectedCourse(course)}
                className={cn(
                  // when active, override the default variant colors:
                  isActive
                    ? "bg-blue-600 text-white hover:bg-blue-700"
                    : "hover:bg-blue-50",
                  // keep consistent spacing and cursor
                  "transition-colors"
                )}
              >
                {course}
              </Button>
            );
          })}
        </div>

        <Button
          onClick={() => navigate("/student/add")}
          className="bg-green-600 text-white hover:bg-green-700 hover:cursor-pointer"
        >
          Admit New Student
        </Button>
      </CardHeader>

      <CardContent>
        {loading ? (
          <div className="text-center py-10">Loading students...</div>
        ) : (
          <>
            <h2 className="text-lg font-medium mb-4">
              {data?.courseName ?? selectedCourse} —{" "}
              {data?.students.length ?? 0} student
              {(data?.students.length ?? 0) !== 1 ? "s" : ""}
            </h2>

            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Roll No.</TableHead>
                  <TableHead>Name</TableHead>
                  <TableHead>Email</TableHead>
                  <TableHead>Phone</TableHead>
                  <TableHead>DOB</TableHead>
                  <TableHead>Gender</TableHead>
                  <TableHead>Father</TableHead>
                  <TableHead>Mother</TableHead>
                  <TableHead>Emergency Contact</TableHead>
                  <TableHead>Admission Date</TableHead>
                  <TableHead>Blood Group</TableHead>
                  <TableHead className="text-right">Actions</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {data?.students.map((s: Student, idx: number) => (
                  <TableRow key={`${s.rollNumber}-${idx}`}>
                    <TableCell>{s.rollNumber}</TableCell>
                    <TableCell>
                      {`${s.firstName} ${s.middleName ?? ""} ${s.surname}`}
                    </TableCell>
                    <TableCell>{s.email}</TableCell>
                    <TableCell>{s.phoneNumber}</TableCell>
                    <TableCell>{formatDate(s.dateOfBirth)}</TableCell>
                    <TableCell>{s.gender}</TableCell>
                    <TableCell>{s.fatherName}</TableCell>
                    <TableCell>{s.motherName}</TableCell>
                    <TableCell>{s.emergencyContact}</TableCell>
                    <TableCell>{formatDate(s.admissionDate)}</TableCell>
                    <TableCell>{s.bloodGroup}</TableCell>
                    <TableCell className="text-right space-x-2">
                      {/* Edit button */}
                      <Button
                        className="bg-blue-600 hover:bg-blue-800 text-white hover:cursor-pointer"
                        size="sm"
                        variant="outline"
                        onClick={() =>
                          navigate(`/student/${s.rollNumber}/edit`)
                        }
                      >
                        Edit
                      </Button>

                      {/* Delete Confirmation */}
                      <AlertDialog
                        open={deleteId === s.rollNumber}
                        onOpenChange={(open) => !open && setDeleteId(null)}
                      >
                        <AlertDialogTrigger asChild>
                          <Button
                            className="bg-red-600 hover:bg-red-800 text-white hover:cursor-pointer"
                            size="sm"
                            variant="outline"
                            onClick={() => setDeleteId(s.rollNumber)}
                          >
                            Delete
                          </Button>
                        </AlertDialogTrigger>

                        <AlertDialogContent className="bg-white">
                          <AlertDialogHeader>
                            <AlertDialogTitle>
                              Confirm Deletion
                            </AlertDialogTitle>
                            <AlertDialogDescription>
                              Are you sure you want to delete {s.firstName}{" "}
                              {s.surname}? This action cannot be undone.
                            </AlertDialogDescription>
                          </AlertDialogHeader>

                          <div className="flex justify-end space-x-2 pt-4">
                            <AlertDialogCancel className="hover:cursor-pointer hover:bg-gray-300">
                              Cancel
                            </AlertDialogCancel>
                            <AlertDialogAction
                              className="bg-red-600 text-white hover:bg-red-800 hover:cursor-pointer"
                              onClick={handleDelete}
                            >
                              Delete
                            </AlertDialogAction>
                          </div>
                        </AlertDialogContent>
                      </AlertDialog>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </>
        )}
      </CardContent>
    </Card>
  );
};

export default StudentList;
