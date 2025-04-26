import { useState } from "react";
import axios from "axios";
import { getTeacherById } from "../../api/TeacherApis";

const Teacher = () => {
  const [teacherId, setTeacherId] = useState("");
  const [teacher, setTeacher] = useState(null);
  const [error, setError] = useState("");

  const handleFetch = async () => {
    try {
      const data = await getTeacherById(teacherId);
      setTeacher(data);
      setError("");
    } catch (err) {
      setTeacher(null);
      setError("Failed to fetch teacher details.");
    }
  };

  return (
    <div className="get-teacher-container">
      <input
        type="text"
        placeholder="Enter Teacher ID"
        value={teacherId}
        onChange={(e) => setTeacherId(e.target.value)}
      />
      <button onClick={handleFetch}>Get Details</button>

      {error && <p className="error">{error}</p>}

      {teacher && (
        <div className="teacher-details">
          <p><strong>First Name:</strong> {teacher.firstName}</p>
          <p><strong>Last Name:</strong> {teacher.lastName}</p>
          <p><strong>Email:</strong> {teacher.email}</p>
          <p><strong>Phone Number:</strong> {teacher.phoneNumber}</p>
          <p><strong>Qualification:</strong> {teacher.qualification}</p>
          <p><strong>Date of Joining:</strong> {teacher.dateOfJoining}</p>
        </div>
      )}
    </div>
  );
};

export default Teacher;
