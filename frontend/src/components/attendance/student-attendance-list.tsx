import React, { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Table } from "@/components/ui/table";
import {
  Select,
  SelectTrigger,
  SelectValue,
  SelectContent,
  SelectItem,
} from "@/components/ui/select";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { toast } from "sonner";
import {
  getAttendanceByStudentRollNo,
  updateAttendance,
} from "@/api/AttendanceApis";

/* -------------------------------------------------------------------------- */
/*                              Interfaces                                    */
/* -------------------------------------------------------------------------- */
interface AttendanceRecord {
  id: number;
  studentId: number;
  rollNumber: string;
  studentName: string;
  attendanceDate: string;
  status: "PRESENT" | "ABSENT" | "LEAVE";
  markedBy: string;
  remarks?: string | null;
}

interface UpdateAttendancePayload {
  studentId: number;
  attendanceDate: string;
  status: "PRESENT" | "ABSENT" | "LEAVE";
  remarks?: string;
  markedBy: string;
}

/* -------------------------------------------------------------------------- */
/*                            Remark Dialog Component                         */
/* -------------------------------------------------------------------------- */
interface RemarkDialogProps {
  recordId: number;
  studentName: string;
  remark: string;
  onRemarkChange: (id: number, value: string) => void;
}

const RemarkDialog: React.FC<RemarkDialogProps> = ({
  recordId,
  studentName,
  remark,
  onRemarkChange,
}) => {
  const [open, setOpen] = useState(false);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button
          variant="outline"
          size="sm"
          className="text-sm w-20 bg-white hover:bg-gray-100"
        >
          {remark ? "Edit" : "Add"}
        </Button>
      </DialogTrigger>

      <DialogContent className="sm:max-w-[425px] bg-white">
        <DialogHeader>
          <DialogTitle>Edit Remark</DialogTitle>
          <DialogDescription>
            Enter or update the remark for <b>{studentName}</b>.
          </DialogDescription>
        </DialogHeader>

        <div className="mt-4">
          <Input
            type="text"
            value={remark}
            placeholder="Type your remark..."
            onChange={(e) => onRemarkChange(recordId, e.target.value)}
            autoFocus
          />
        </div>

        <DialogFooter>
          <Button
            onClick={() => {
              toast.success(`Remark updated for ${studentName}`);
              setOpen(false);
            }}
            className="bg-[#3D348B] text-white hover:bg-[#2E2874]"
          >
            Save
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
};

/* -------------------------------------------------------------------------- */
/*                         Main Student Attendance List                       */
/* -------------------------------------------------------------------------- */
const StudentAttendanceList: React.FC = () => {
  const [rollNumber, setRollNumber] = useState<string>("");
  const [records, setRecords] = useState<AttendanceRecord[]>([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(false);
  const [editedRemarks, setEditedRemarks] = useState<Record<number, string>>(
    {}
  );
  const [editedStatus, setEditedStatus] = useState<
    Record<number, "PRESENT" | "ABSENT" | "LEAVE" | "">
  >({});

  /* -------------------------------------------------------------------------- */
  /*                              Fetch Attendance                              */
  /* -------------------------------------------------------------------------- */
  const fetchAttendance = async (pageNumber = 0) => {
    if (!rollNumber.trim()) {
      toast.error("Please enter a roll number");
      return;
    }
    try {
      setLoading(true);
      const res = await getAttendanceByStudentRollNo(
        rollNumber,
        pageNumber,
        10
      );
      setRecords(res.content as AttendanceRecord[]);
      setPage(res.number);
      setTotalPages(res.totalPages);
    } catch (err) {
      toast.error("Failed to fetch attendance records");
      console.error("❌ Error fetching attendance:", err);
    } finally {
      setLoading(false);
    }
  };

  /* -------------------------------------------------------------------------- */
  /*                              Update Attendance                             */
  /* -------------------------------------------------------------------------- */
  const handleUpdate = async (id: number) => {
    try {
      const updatedStatus = editedStatus[id];
      const updatedRemarks = editedRemarks[id];
      const record = records.find((r) => r.id === id);
      if (!record) return;

      if (
        !updatedStatus &&
        (updatedRemarks === undefined || updatedRemarks === record.remarks)
      ) {
        toast.error("No changes to update");
        return;
      }

      const payload: UpdateAttendancePayload = {
        studentId: record.studentId,
        attendanceDate: record.attendanceDate,
        status: (updatedStatus || record.status) as
          | "PRESENT"
          | "ABSENT"
          | "LEAVE",
        remarks: updatedRemarks ?? record.remarks ?? "",
        markedBy: "Admin MohanTalavar", // TODO: Replace with logged-in user info
      };

      await updateAttendance(id, payload as any);
      toast.success("Attendance updated successfully!");
      await fetchAttendance(page);
    } catch (err) {
      toast.error("Failed to update record");
      console.error("❌ Error updating record:", err);
    }
  };

  /* -------------------------------------------------------------------------- */
  /*                                 Render                                     */
  /* -------------------------------------------------------------------------- */
  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold text-[#3D348B] mb-6">
        Student Attendance Records
      </h2>

      {/* Search Section */}
      <div className="flex gap-4 items-end mb-6 flex-wrap">
        <div>
          <label className="block mb-1 text-sm font-medium text-gray-600">
            Enter Roll Number
          </label>
          <Input
            value={rollNumber}
            onChange={(e) => setRollNumber(e.target.value)}
            placeholder="e.g. 001"
            className="w-48"
          />
        </div>

        <Button
          onClick={() => fetchAttendance(0)}
          className="bg-[#3D348B] hover:bg-[#2E2874] text-white hover:cursor-pointer"
          disabled={loading}
        >
          {loading ? "Loading..." : "Fetch Attendance"}
        </Button>
      </div>

      {/* Attendance Table */}
      {records.length > 0 ? (
        <div className="overflow-x-auto rounded-xl shadow">
          <Table className="w-full border">
            <thead className="bg-[#7678ED] text-white">
              <tr>
                <th className="py-3 px-4 text-left">Date</th>
                <th className="py-3 px-4 text-left">Status</th>
                <th className="py-3 px-4 text-left">Remarks</th>
                <th className="py-3 px-4 text-left">Marked By</th>
                <th className="py-3 px-4 text-left">Action</th>
              </tr>
            </thead>
            <tbody>
              {records.map((rec) => (
                <tr key={rec.id} className="border-b hover:bg-gray-50">
                  <td className="py-2 px-4">{rec.attendanceDate}</td>

                  {/* Editable Status */}
                  <td className="py-2 px-4">
                    <Select
                      value={editedStatus[rec.id] ?? rec.status ?? ""}
                      onValueChange={(val) =>
                        setEditedStatus((prev) => ({
                          ...prev,
                          [rec.id]: val as "PRESENT" | "ABSENT" | "LEAVE",
                        }))
                      }
                    >
                      <SelectTrigger className="w-32">
                        <SelectValue placeholder="Status" />
                      </SelectTrigger>
                      <SelectContent className="bg-white">
                        <SelectItem value="PRESENT">Present</SelectItem>
                        <SelectItem value="ABSENT">Absent</SelectItem>
                        <SelectItem value="LEAVE">Leave</SelectItem>
                      </SelectContent>
                    </Select>
                  </td>

                  {/* Remarks with dialog and responsive behavior */}
                  <td className="py-2 px-4 align-top">
                    <div className="grid grid-cols-[1fr_auto] items-start gap-x-3 max-w-[420px]">
                      {/* Hidden on small devices */}
                      <div className="hidden sm:block text-sm text-gray-700 italic leading-snug break-all whitespace-pre-wrap">
                        {editedRemarks[rec.id] ??
                          rec.remarks ??
                          "No remark added"}
                      </div>

                      {/* Edit/Add button */}
                      <div className="flex justify-end items-start">
                        <RemarkDialog
                          recordId={rec.id}
                          studentName={rec.studentName}
                          remark={editedRemarks[rec.id] ?? rec.remarks ?? ""}
                          onRemarkChange={(id, val) =>
                            setEditedRemarks((prev) => ({ ...prev, [id]: val }))
                          }
                        />
                      </div>
                    </div>
                  </td>

                  <td className="py-2 px-4 text-gray-700">{rec.markedBy}</td>

                  <td className="py-2 px-4">
                    <Button
                      onClick={() => handleUpdate(rec.id)}
                      className="bg-[#F35B04] hover:bg-[#d14d03] text-white hover:cursor-pointer"
                    >
                      Update
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      ) : (
        <p className="text-gray-500 text-center mt-6">No records to display</p>
      )}

      {/* Pagination */}
      {records.length > 0 && (
        <div className="flex justify-end items-center gap-4 mt-6">
          <Button
            disabled={page === 0}
            onClick={() => fetchAttendance(page - 1)}
            variant="outline"
          >
            Prev
          </Button>
          <span>
            Page {page + 1} of {totalPages}
          </span>
          <Button
            disabled={page + 1 >= totalPages}
            onClick={() => fetchAttendance(page + 1)}
            variant="outline"
          >
            Next
          </Button>
        </div>
      )}
    </div>
  );
};

export default StudentAttendanceList;
