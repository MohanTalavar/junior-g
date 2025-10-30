// src/components/attendance/attendance-list-component.tsx
import React, { useState } from "react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Table } from "@/components/ui/table";
import { toast } from "sonner";
import { getCourseWithStudentsWithId } from "@/api/StudentApis";
import {
  getAttendanceByDate,
  markAttendanceBulk,
  type Attendance,
} from "@/api/AttendanceApis";

const AttendanceList: React.FC = () => {
  const [course, setCourse] = useState<string>("");
  const getToday = () => new Date().toISOString().split("T")[0];
  const [date, setDate] = useState<string>(getToday);
  const [students, setStudents] = useState<any[]>([]);
  const [attendanceData, setAttendanceData] = useState<Record<number, string>>(
    {}
  );
  const [remarksData, setRemarksData] = useState<Record<number, string>>({});
  const [markedByData, setMarkedByData] = useState<Record<number, string>>({});
  /* -------------------------------------------------------------------------- */
  /*                           Fetch Students + Attendance                      */
  /* -------------------------------------------------------------------------- */
  const fetchStudents = async () => {
    if (!course) {
      toast.error("Please select a course");
      return;
    }
    if (!date) {
      toast.error("Please select a date");
      return;
    }

    try {
      console.groupCollapsed("🟣 Attendance Fetch Debug Log");
      console.log("Selected Course:", course);
      console.log("Selected Date:", date);

      // 1️⃣ Fetch course + student list
      const courseData = await getCourseWithStudentsWithId(course);
      const studentList = courseData.students || [];
      console.log("📘 Students fetched:", studentList.length);
      console.table(
        studentList.map((s: any) => ({
          id: s.id,
          name: `${s.firstName} ${s.surname}`,
          rollNumber: s.rollNumber,
        }))
      );

      // 2️⃣ Fetch attendance for selected date
      const attendanceRecords = await getAttendanceByDate(date);
      console.log("🟡 Attendance records fetched:", attendanceRecords.length);
      console.table(
        attendanceRecords.map((a: any) => ({
          id: a.id,
          studentId: a.studentId,
          status: a.status,
          remarks: a.remarks,
          markedBy: a.markedBy,
        }))
      );

      // 3️⃣ Build a map of studentId → record
      const existingAttendanceMap: Record<
        number,
        { status: string; remarks?: string; markedBy?: string }
      > = {};
      attendanceRecords.forEach((record: any) => {
        if (record.studentId) {
          existingAttendanceMap[record.studentId] = {
            status: record.status,
            remarks: record.remarks,
            markedBy: record.markedBy,
          };
        }
      });

      console.log(
        "🗺️ existingAttendanceMap (studentId → record):",
        existingAttendanceMap
      );

      // 🔹 Get user + formatted role (same as handleSubmit)
      const role = localStorage.getItem("role") || "ROLE_UNKNOWN";
      const user = localStorage.getItem("user") || "UnknownUser";
      const formattedRole =
        role === "ROLE_ADMIN"
          ? "Admin"
          : role === "ROLE_TEACHER"
          ? "Teacher"
          : "User";

      // 4️⃣ Merge attendance with student list
      setStudents(studentList);

      const prefillStatus: Record<number, string> = {};
      const prefillRemarks: Record<number, string> = {};
      const prefillMarkedBy: Record<number, string> = {};

      studentList.forEach((s: any) => {
        const existing = existingAttendanceMap[s.id];
        prefillStatus[s.id] = existing?.status ?? "";
        prefillRemarks[s.id] = existing?.remarks ?? "";
        prefillMarkedBy[s.id] =
          existing?.markedBy || `${formattedRole} ${user}`;
      });

      console.log("✅ prefillData (studentId → status):", prefillStatus);
      console.log("✅ prefillRemarks:", prefillRemarks);
      console.log("✅ prefillMarkedBy:", prefillMarkedBy);

      setAttendanceData(prefillStatus);
      setRemarksData(prefillRemarks);
      setMarkedByData(prefillMarkedBy);

      console.groupEnd();
      toast.success(`Loaded ${studentList.length} students for ${course}`);
    } catch (err) {
      console.error("❌ Error during fetchStudents():", err);
      toast.error("Failed to fetch students or attendance records");
    }
  };

  const handleRemarksChange = (studentId: number, remark: string) => {
    setRemarksData((prev) => ({ ...prev, [studentId]: remark }));
  };

  /* -------------------------------------------------------------------------- */
  /*                          Handle Status Change                              */
  /* -------------------------------------------------------------------------- */
  const handleStatusChange = (studentId: number, status: string) => {
    setAttendanceData((prev) => ({ ...prev, [studentId]: status }));
  };

  /* -------------------------------------------------------------------------- */
  /*                              Submit Attendance                             */
  /* -------------------------------------------------------------------------- */
  const handleSubmit = async () => {
    if (!date) {
      toast.error("Please select a date before submitting");
      return;
    }

    // Get user details from localStorage
    const role = localStorage.getItem("role") || "ROLE_UNKNOWN";
    const user = localStorage.getItem("user") || "UnknownUser";
    const formattedRole =
      role === "ROLE_ADMIN"
        ? "Admin"
        : role === "ROLE_TEACHER"
        ? "Teacher"
        : "User";
    const markedBy = `${formattedRole} ${user}`;

    // Build attendance list to send to backend
    const attendanceList: Attendance[] = students.map((s) => ({
      studentId: s.id,
      attendanceDate: date,
      status: (attendanceData[s.id] || "ABSENT") as
        | "PRESENT"
        | "ABSENT"
        | "LEAVE",
      markedBy,
      remarks: remarksData[s.id] || "",
    }));

    try {
      console.log("📤 Submitting attendanceList:", attendanceList);
      const response = await markAttendanceBulk(attendanceList);

      // ✅ If backend returns updated records, reflect them in UI
      if (Array.isArray(response) && response.length > 0) {
        const updatedStatus: Record<number, string> = {};
        const updatedRemarks: Record<number, string> = {};

        response.forEach((rec: any) => {
          if (rec.studentId) {
            updatedStatus[rec.studentId] = rec.status || "";
            updatedRemarks[rec.studentId] = rec.remarks || "";
          }
        });

        setAttendanceData((prev) => ({ ...prev, ...updatedStatus }));
        setRemarksData((prev) => ({ ...prev, ...updatedRemarks }));

        console.log("✅ Updated from backend:", {
          updatedStatus,
          updatedRemarks,
        });
      } else {
        // If no response returned → re-fetch students + attendance for this date
        await fetchStudents();
      }

      toast.success("Attendance submitted successfully!");
    } catch (err) {
      console.error("❌ Error submitting attendance:", err);
      toast.error("Error submitting attendance");
    }
  };

  /* -------------------------------------------------------------------------- */
  /*                                Render UI                                   */
  /* -------------------------------------------------------------------------- */
  return (
    <div className="p-6">
      {/* Filter Section */}
      <div className="flex flex-wrap gap-4 items-end mb-6">
        <div>
          <label className="block mb-1 text-sm font-medium text-gray-600">
            Select Course
          </label>
          <Select onValueChange={(val) => setCourse(val)}>
            <SelectTrigger className="w-48">
              <SelectValue placeholder="Choose Course" />
            </SelectTrigger>
            <SelectContent className="bg-white">
              <SelectItem value="PlayGroup">Play Group</SelectItem>
              <SelectItem value="Nursery">Nursery</SelectItem>
              <SelectItem value="LKG">LKG</SelectItem>
              <SelectItem value="UKG">UKG</SelectItem>
              <SelectItem value="Daycare">Daycare</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div>
          <label className="block mb-1 text-sm font-medium text-gray-600">
            Select Date
          </label>
          <Input
            type="date"
            className="w-48"
            value={date}
            max={new Date().toISOString().split("T")[0]} // ✅ disallow future dates
            onChange={(e) => setDate(e.target.value)}
          />
        </div>

        <Button
          onClick={fetchStudents}
          className="bg-[#3D348B] text-white hover:bg-[#2E2874] hover:cursor-pointer"
        >
          Fetch Students
        </Button>
      </div>

      {/* Attendance Table */}
      {students.length > 0 ? (
        <div className="overflow-x-auto rounded-xl shadow">
          <Table className="w-full border">
            <thead className="bg-[#7678ED] text-white">
              <tr>
                <th className="py-3 px-4 text-left">Roll No</th>
                <th className="py-3 px-4 text-left">Name</th>
                <th className="py-3 px-4 text-left">Status</th>
                <th className="py-3 px-4 text-left">Remarks</th>
                <th className="py-3 px-4 text-left">Marked By</th>
              </tr>
            </thead>
            <tbody>
              {students.map((stud) => (
                <tr key={stud.id} className="border-b hover:bg-gray-50">
                  <td className="py-2 px-4">{stud.rollNumber}</td>
                  <td className="py-2 px-4">{`${stud.firstName} ${stud.surname}`}</td>

                  {/* STATUS */}
                  <td className="py-2 px-4">
                    <Select
                      value={attendanceData[stud.id] || ""}
                      onValueChange={(val) => handleStatusChange(stud.id, val)}
                    >
                      <SelectTrigger className="w-36">
                        <SelectValue placeholder="Not Marked" />
                      </SelectTrigger>
                      <SelectContent className="bg-white">
                        <SelectItem value="PRESENT">Present</SelectItem>
                        <SelectItem value="ABSENT">Absent</SelectItem>
                        <SelectItem value="LEAVE">Leave</SelectItem>
                      </SelectContent>
                    </Select>
                  </td>

                  {/* REMARKS */}
                  <td className="py-2 px-4">
                    <Input
                      type="text"
                      placeholder="Add remark"
                      value={remarksData[stud.id] || ""}
                      onChange={(e) =>
                        handleRemarksChange(stud.id, e.target.value)
                      }
                    />
                  </td>

                  {/* MARKED BY */}
                  <td className="py-2 px-4 text-gray-700">
                    {markedByData[stud.id]}
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      ) : (
        <p className="text-gray-500 text-center mt-6">No students to display</p>
      )}

      {/* Submit Button */}
      {students.length > 0 && (
        <div className="mt-6 text-right">
          <Button
            onClick={handleSubmit}
            className="bg-[#F35B04] hover:bg-[#d14d03] text-white font-semibold px-6 py-2 hover:cursor-pointer"
          >
            Submit Attendance
          </Button>
        </div>
      )}
    </div>
  );
};

export default AttendanceList;
