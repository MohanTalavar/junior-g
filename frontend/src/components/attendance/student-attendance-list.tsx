// src/components/attendance/student-attendance-list.tsx
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
import { toast } from "sonner";
import {
  getAttendanceByStudentRollNo,
  updateAttendance,
} from "@/api/AttendanceApis";

/**
 * This local interface mirrors AttendanceResponseDto from backend.
 */
interface AttendanceRecord {
  id: number;
  studentId: number;
  rollNumber: string;
  studentName: string;
  attendanceDate: string; // ISO date string e.g. "2025-10-28"
  status: "PRESENT" | "ABSENT" | "LEAVE";
  markedBy: string;
  remarks?: string | null;
}

interface UpdateAttendancePayload {
  studentId: number;
  // rollNumber is optional on backend DTO; not necessary for update but harmless if present.
  // We're not sending id (backend uses path variable).
  attendanceDate: string;
  status: "PRESENT" | "ABSENT" | "LEAVE";
  remarks?: string;
  markedBy: string;
}

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
  /*                            Handle Update Attendance                         */
  /* -------------------------------------------------------------------------- */
  const handleUpdate = async (id: number) => {
    try {
      const updatedStatus = editedStatus[id];
      const updatedRemarks = editedRemarks[id];

      if (
        !updatedStatus &&
        (updatedRemarks === undefined ||
          updatedRemarks === records.find((r) => r.id === id)?.remarks)
      ) {
        toast.error("No changes to update");
        return;
      }

      const record = records.find((r) => r.id === id);
      if (!record) return;

      // Build payload matching backend AttendanceDto (no id in body)
      const payload: UpdateAttendancePayload = {
        studentId: record.studentId,
        attendanceDate: record.attendanceDate,
        status: (updatedStatus || record.status) as
          | "PRESENT"
          | "ABSENT"
          | "LEAVE",
        remarks: updatedRemarks ?? record.remarks ?? "",
        markedBy: "Admin MohanTalavar", // TODO: replace with real user from slice
      };

      await updateAttendance(id, payload as any); // cast to any if updateAttendance's TS type differs

      toast.success("Attendance updated successfully!");
      await fetchAttendance(page);
    } catch (err) {
      toast.error("Failed to update record");
      console.error("❌ Error updating record:", err);
    }
  };

  /* -------------------------------------------------------------------------- */
  /*                                   Render                                   */
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

                  {/* Editable Remarks */}
                  <td className="py-2 px-4">
                    <Input
                      type="text"
                      placeholder="Add remarks"
                      value={editedRemarks[rec.id] ?? rec.remarks ?? ""}
                      onChange={(e) =>
                        setEditedRemarks((prev) => ({
                          ...prev,
                          [rec.id]: e.target.value,
                        }))
                      }
                    />
                  </td>

                  {/* Marked By */}
                  <td className="py-2 px-4 text-gray-700">{rec.markedBy}</td>

                  {/* Update Button */}
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

      {/* Pagination Controls */}
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
