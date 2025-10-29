import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  getTeacherList,
  TeacherWithCourse,
  deleteTeacherById,
} from "../../api/TeacherApis";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
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
import { useAuth } from "@/hooks/useAuth"; // your auth hook
import { toast } from "sonner";

const TeacherList: React.FC = () => {
  const { role, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const [teachers, setTeachers] = useState<TeacherWithCourse[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [deleteId, setDeleteId] = useState<number | null>(null);

  const loadTeachers = async () => {
    setLoading(true);
    try {
      const list = await getTeacherList();
      setTeachers(list);
    } catch (err) {
      console.error(err);
      toast.error("Failed to load teachers. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (isAuthenticated && role === "ROLE_ADMIN") {
      loadTeachers();
    }
  }, [isAuthenticated, role]);

  if (!isAuthenticated || role !== "ROLE_ADMIN") {
    return <div>You are not authorized to view this page.</div>;
  }

  const handleDelete = async () => {
    if (deleteId === null) return;
    try {
      await deleteTeacherById(deleteId);
      toast.success("Teacher deleted successfully");
      setDeleteId(null);
      await loadTeachers();
    } catch (err) {
      console.error(err);
      toast.error("Failed to delete teacher.");
    }
  };

  return (
    <Card className="space-y-4">
      <CardHeader className="flex flex-row items-center justify-between">
        <CardTitle className="text-3xl font-bold text-[#3D348B]">
          Teacher Management
        </CardTitle>
        <Button
          onClick={() => navigate("/admin/teachers/add")}
          className="bg-green-600 text-white hover:bg-green-700 hover:cursor-pointer"
        >
          Hire New Teacher
        </Button>
      </CardHeader>
      <CardContent>
        {loading ? (
          <div className="text-center py-10">Loading...</div>
        ) : (
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Name</TableHead>
                <TableHead>Email</TableHead>
                <TableHead>Phone</TableHead>
                <TableHead>Qualification</TableHead>
                <TableHead>Date of Joining</TableHead>
                <TableHead>Courses</TableHead>
                <TableHead className="text-right">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {teachers.map((teacher) => (
                <TableRow key={teacher.id}>
                  <TableCell>
                    {teacher.firstName} {teacher.lastName}
                  </TableCell>
                  <TableCell>{teacher.email}</TableCell>
                  <TableCell>{teacher.phoneNumber}</TableCell>
                  <TableCell>{teacher.qualification}</TableCell>
                  <TableCell>{teacher.dateOfJoining}</TableCell>
                  <TableCell>
                    {teacher.courses?.length > 0
                      ? teacher.courses.join(",")
                      : "NONE"}
                  </TableCell>
                  <TableCell className="text-right space-x-2">
                    {/* <Button
                      size="sm"
                      onClick={() => navigate(`/admin/teachers/${teacher.id}`)}
                    >
                      View
                    </Button> */}
                    <Button
                      className="bg-blue-600 hover:bg-blue-800 text-white hover:cursor-pointer"
                      size="sm"
                      variant="outline"
                      onClick={() =>
                        navigate(`/admin/teachers/${teacher.id}/edit`)
                      }
                    >
                      Edit
                    </Button>
                    {/* Delete Confirmation */}
                    <AlertDialog
                      open={deleteId === teacher.id}
                      onOpenChange={(open) => !open && setDeleteId(null)}
                    >
                      <AlertDialogTrigger asChild>
                        <Button
                          className="bg-red-600 hover:bg-red-800 text-white hover:cursor-pointer"
                          size="sm"
                          variant="outline"
                          onClick={() => setDeleteId(teacher.id)}
                        >
                          Delete
                        </Button>
                      </AlertDialogTrigger>
                      <AlertDialogContent className="bg-white">
                        <AlertDialogHeader>
                          <AlertDialogTitle>Confirm Deletion</AlertDialogTitle>
                          <AlertDialogDescription>
                            Are you sure you want to delete {teacher.firstName}{" "}
                            {teacher.lastName}? This action cannot be undone.
                          </AlertDialogDescription>
                        </AlertDialogHeader>
                        <div className="flex justify-end space-x-2 pt-4">
                          <AlertDialogCancel className="hover:cursor-pointer hover:bg-gray-300">
                            Cancel
                          </AlertDialogCancel>
                          <AlertDialogAction
                            className="bg-red-600 hover:bg-red-800 text-white hover:cursor-pointer"
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
        )}
      </CardContent>
    </Card>
  );
};

export default TeacherList;
