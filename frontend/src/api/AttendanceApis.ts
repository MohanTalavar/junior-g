import api from "./Junior_G_Apis";

/* -------------------------------------------------------------------------- */
/*                                Type Definitions                            */
/* -------------------------------------------------------------------------- */

/** Mirrors AttendanceDto.java */
export type Attendance = {
  studentId: number;
  attendanceDate: string; // ISO format: yyyy-MM-dd
  status: "PRESENT" | "ABSENT" | "LEAVE";
  markedBy?: string;
  remarks?: string;
};

/** Mirrors AttendanceResponseDto.java */
export type AttendanceResponse = {
    id : number;
    studentId : number;
    rollNumber : string;
    studentName : string;
    attendanceDate  : string; // ISO format
    status : "PRESENT" | "ABSENT" | "LEAVE"; // ENUM in BE AttendanceStatus
    markedBy : string;
    remarks?: string;
};

/** Paginated Response Wrapper */
export type PaginatedResponse<T> = {
    content : T[];
    totalPages: number;
    totalElements : number;
    number: number;
    size : number;
};

/* -------------------------------------------------------------------------- */
/*                                API METHODS                                 */
/* -------------------------------------------------------------------------- */

const BASE_URL = "/attendances";

/**
 * Mark attendance for a single student.
 * POST /attendances/mark
 */
export const markAttendance = async(attendance : Attendance): Promise<AttendanceResponse> =>{

    const res = await api.post(`${BASE_URL}/mark`, attendance);
    return res.data;
};

/**
 * Mark attendance in bulk for multiple students.
 * POST /attendances/mark/bulk
 */
export const markAttendanceBulk = async(attendanceList : Attendance[]) : Promise<AttendanceResponse[]> =>{

    const res = await api.post(`${BASE_URL}/mark/bulk`, attendanceList);
    return res.data;
}

/**
 * Fetch all attendance records for a student.
 * GET /attendances/student/{studentId}
 */
export const getAttendanceByStudent = async (studentId: number): Promise<AttendanceResponse[]> => {
  const res = await api.get(`${BASE_URL}/student/${studentId}`);
  return res.data;
};

/**
 * Fetch attendance by a specific date.
 * GET /attendances/date/{date}
 */
export const getAttendanceByDate = async (date: string): Promise<AttendanceResponse[]> => {
  const res = await api.get(`${BASE_URL}/date/${date}`);
  return res.data;
};

/**
 * Fetch attendance records between two dates (paginated).
 * GET /attendances/range?startDate=...&endDate=...&page=...&size=...
 */
export const getAttendanceByDateRange = async (
  startDate: string,
  endDate: string,
  page = 0,
  size = 10
): Promise<PaginatedResponse<AttendanceResponse>> => {
  const res = await api.get(`${BASE_URL}/range`, {
    params: { startDate, endDate, page, size },
  });
  return res.data;
};

export const getAttendanceByStudentRollNo = async (
  rollNumber: string,
  page: number = 0,
  size: number = 10
) => {
  try {
    const response = await api.get(`/attendances/studentByRollNo/${rollNumber}`, {
      params: { page, size },
    });
    return response.data; // contains Page<AttendanceResponseDto>
  } catch (error: any) {
    console.error("❌ Error fetching student attendance:", error);
    throw error.response?.data || error;
  }
};

/**
 * Update a specific attendance record.
 * PUT /attendances/{attendanceId}
 */
export const updateAttendance = async (
  attendanceId: number,
  attendance: Attendance
): Promise<AttendanceResponse> => {
  const res = await api.put(`${BASE_URL}/${attendanceId}`, attendance);
  return res.data;
};

/**
 * Delete a specific attendance record.
 * DELETE /attendances/{attendanceId}
 */
export const deleteAttendance = async (attendanceId: number): Promise<boolean> => {
  const res = await api.delete(`${BASE_URL}/${attendanceId}`);
  return res.status === 204;
};
