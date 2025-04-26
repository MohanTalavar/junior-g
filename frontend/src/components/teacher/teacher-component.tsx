import { useState } from "react";
import { getTeacherById, Teacher } from "../../api/TeacherApis";

const TeacherComponent: React.FC = () => {
  const [teacherId, setTeacherId] = useState<string>("");
  const [teacher, setTeacher] = useState<Teacher | null>(null);
  const [error, setError] = useState<string>("");

  const handleFetch = async () => {
    if (!teacherId.trim) {
      setError("Please enter a valid Teacher ID.");
      setTeacher(null);
      return;
    }
    try {
      const id = parseInt(teacherId, 10);
      const data = await getTeacherById(id);
      setTeacher(data);
      setError("");
    } catch (err) {
      setTeacher(null);
      setError("Failed to fetch teacher details. Please try again.");
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
          <p>
            <strong>First Name:</strong> {teacher.firstName}
          </p>
          <p>
            <strong>Last Name:</strong> {teacher.lastName}
          </p>
          <p>
            <strong>Email:</strong> {teacher.email}
          </p>
          <p>
            <strong>Phone Number:</strong> {teacher.phoneNumber}
          </p>
          <p>
            <strong>Qualification:</strong> {teacher.qualification}
          </p>
          <p>
            <strong>Date of Joining:</strong> {teacher.dateOfJoining}
          </p>
        </div>
      )}
    </div>
  );
};

export default TeacherComponent;
