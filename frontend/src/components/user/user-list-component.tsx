// src/components/admin/UserList.tsx
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getListOfusers, User, deleteUser } from "@/api/UserApis";
import { useAuth } from "@/hooks/useAuth";
import { toast } from "sonner";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
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

export function UserList() {
  const { role, isAuthenticated } = useAuth();
  const navigate = useNavigate();

  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [deleteId, setDeleteId] = useState<string | null>(null);
  const ALLOWED_ROLES = ["ROLE_ADMIN"];

  // Fetch users
  const loadUsers = async () => {
    setLoading(true);
    try {
      const data = await getListOfusers();
      setUsers(data);
    } catch (err) {
      console.error(err);
      toast.error("Failed to load users. Admin access may be required.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (isAuthenticated && ALLOWED_ROLES.includes(role || "")) {
      loadUsers();
    }
  }, [isAuthenticated, role]);

  if (!isAuthenticated || !ALLOWED_ROLES.includes(role || "")) {
    return (
      <div className="p-4 text-center">
        You are not authorized to view this page.
      </div>
    );
  }

  const handleDelete = async () => {
    if (!deleteId) return;

    try {
      await deleteUser(deleteId);
      toast.success("User deleted successfully");
      setDeleteId(null);
      await loadUsers();
    } catch (err) {
      console.error(err);
      toast.error("Failed to delete user.");
    }
  };

  return (
    <Card className="space-y-4">
      <CardHeader className="flex items-center justify-between">
        <CardTitle className="text-2xl font-bold">User Management</CardTitle>
        <Button
          onClick={() => navigate("/admin/add-user")}
          className="bg-green-600 text-white hover:bg-green-700"
        >
          Add User
        </Button>
      </CardHeader>

      <CardContent>
        {loading ? (
          <div className="text-center py-10">Loading users...</div>
        ) : (
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Sr.No</TableHead>
                <TableHead>Username</TableHead>
                <TableHead>Email</TableHead>
                <TableHead>Role</TableHead>
                <TableHead className="text-right">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {users.map((u, idx) => (
                <TableRow key={u.userName}>
                  <TableCell>{idx + 1}</TableCell>
                  <TableCell>{u.userName}</TableCell>
                  <TableCell>{u.email}</TableCell>
                  <TableCell>{u.role.replace("ROLE_", "")}</TableCell>
                  <TableCell className="text-right space-x-2">
                    <Button
                      size="sm"
                      variant="outline"
                      className="bg-blue-600 text-white hover:bg-blue-800 hover:cursor-pointer"
                      onClick={() => navigate(`/admin/edit-user/${u.userName}`)}
                    >
                      Edit
                    </Button>

                    <AlertDialog
                      open={deleteId === u.userName}
                      onOpenChange={(open) => !open && setDeleteId(null)}
                    >
                      <AlertDialogTrigger asChild>
                        <Button
                          size="sm"
                          variant="destructive"
                          className="bg-red-600 text-white hover:bg-red-800 hover:cursor-pointer"
                          onClick={() => setDeleteId(u.userName)}
                        >
                          Delete
                        </Button>
                      </AlertDialogTrigger>
                      <AlertDialogContent className="bg-white">
                        <AlertDialogHeader>
                          <AlertDialogTitle>Confirm Deletion</AlertDialogTitle>
                          <AlertDialogDescription>
                            Are you sure you want to delete{" "}
                            <strong>{u.userName}</strong>? This action cannot be
                            undone.
                          </AlertDialogDescription>
                        </AlertDialogHeader>
                        <div className="flex justify-end space-x-2 pt-4">
                          <AlertDialogCancel className="hover:bg-gray-200 hover:cursor-pointer">
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
        )}
      </CardContent>
    </Card>
  );
}
