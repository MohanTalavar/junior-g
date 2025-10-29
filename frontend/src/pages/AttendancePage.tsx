// src/pages/AttendancePage.tsx
import React from "react";
import AttendanceList from "@/components/attendance/attendance-list-component";
import StudentAttendanceList from "@/components/attendance/student-attendance-list";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"; // ✅ from ShadCN UI

const AttendancePage: React.FC = () => {
  return (
    <div className="p-6">
      {/* Page Header */}
      <h2 className="text-3xl font-bold text-[#3D348B] mb-6">
        Attendance Management
      </h2>

      {/* Internal Navbar / Tab Navigation */}
      <Tabs defaultValue="mark" className="w-full">
        {/* Tab Buttons */}
        <TabsList className="flex justify-start bg-[#f3f4ff] rounded-xl shadow-sm mb-6">
          <TabsTrigger
            value="mark"
            className="text-[#3D348B] data-[state=active]:bg-[#3D348B] data-[state=active]:text-white px-6 py-2 rounded-l-lg hover:cursor-pointer"
          >
            Mark Attendance
          </TabsTrigger>
          <TabsTrigger
            value="student"
            className="text-[#3D348B] data-[state=active]:bg-[#3D348B] data-[state=active]:text-white px-6 py-2 rounded-r-lg hover: cursor-pointer"
          >
            Student Attendance
          </TabsTrigger>
        </TabsList>

        {/* Components under each tab */}
        <TabsContent value="mark">
          <div className="bg-white p-4 rounded-xl shadow-md border">
            <AttendanceList />
          </div>
        </TabsContent>

        <TabsContent value="student">
          <div className="bg-white p-4 rounded-xl shadow-md border">
            <StudentAttendanceList />
          </div>
        </TabsContent>
      </Tabs>
    </div>
  );
};

export default AttendancePage;
