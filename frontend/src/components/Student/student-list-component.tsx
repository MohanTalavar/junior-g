// src/components/admin/StudentList.tsx
import React, { useEffect, useState } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";
import { useAuth } from "@/hooks/useAuth";
import {
  getCourseWithStudents,
  CourseWithStudents,
  Student,
} from "@/api/StudentApis";
import { toast } from "sonner";

const COURSES = ["Daycare", "Playgroup", "Nursery", "LKG", "UKG"] as const;

const StudentList: React.FC = () => {
  const { role, isAuthenticated } = useAuth();
  const [selectedCourse, setSelectedCourse] =
    useState<(typeof COURSES)[number]>("Daycare");
  const [data, setData] = useState<CourseWithStudents | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    if (!isAuthenticated || role !== "ROLE_ADMIN") return;

    const load = async () => {
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

    load();
  }, [selectedCourse, isAuthenticated, role]);

  if (!isAuthenticated || role !== "ROLE_ADMIN") {
    return <div>You are not authorized to view this page.</div>;
  }

  return (
    <Card className="space-y-4">
      <CardHeader className="flex flex-col md:flex-row md:items-center md:justify-between space-y-2 md:space-y-0">
        <CardTitle className="text-bold text-2xl">Student Management</CardTitle>
        <div className="flex flex-wrap gap-2">
          {COURSES.map((course) => (
            <Button
              key={course}
              size="sm"
              variant={selectedCourse === course ? "default" : "outline"}
              onClick={() => setSelectedCourse(course)}
            >
              {course}
            </Button>
          ))}
        </div>
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
                    <TableCell>{s.dateOfBirth}</TableCell>
                    <TableCell>{s.gender}</TableCell>
                    <TableCell>{s.fatherName}</TableCell>
                    <TableCell>{s.motherName}</TableCell>
                    <TableCell>{s.emergencyContact}</TableCell>
                    <TableCell>{s.admissionDate}</TableCell>
                    <TableCell>{s.bloodGroup}</TableCell>
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
