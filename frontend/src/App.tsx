import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/navbar/navbar-component";
import Home from "./pages/home-page";
import Login from "./pages/login-page";
import TeacherPage from "./pages/teacher-page";
import Logout from "./pages/logout-page";
import CoursePage from "./pages/CoursePage";
// import SummerCampPage from "./pages/summer-camp-page";
import AdmissionEnquiryPage from "./pages/AdmissionEnquiryPage";
import { Toaster } from "./components/ui/sonner";
import { useMediaQuery } from "react-responsive";
import TeacherEdit from "./components/teacher/teacher-edit-component";
import TeacherAdd from "./components/teacher/teacher-add-component";
import StudentPage from "./pages/StudentPage";
import StudentAdd from "./components/Student/student-add-component";
import StudentEdit from "./components/Student/student-edit-component";
import ForgotPasswordPage from "./pages/ForgotPasswordPage";
import { ResetPasswordForm } from "./components/login/reset-password-component";
import { UserList } from "./components/user/user-list-component";
import { UserAddComponent } from "./components/user/user-add-component";
import { UserEditComponent } from "./components/user/user-edit-component";

function App() {
  const isMobile = useMediaQuery({ maxWidth: 768 });

  return (
    <>
      <Router>
        <Navbar />
        <Toaster position={isMobile ? "top-center" : "bottom-right"} />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/teacher" element={<TeacherPage />}></Route>
          <Route path="/admin/teachers/:id/edit" element={<TeacherEdit />} />
          <Route path="/admin/teachers/add" element={<TeacherAdd />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="/course" element={<CoursePage />} />
          <Route path="/student" element={<StudentPage />} />
          <Route path="/student/add" element={<StudentAdd />} />
          <Route path="/student/:rollNumber/edit" element={<StudentEdit />} />
          <Route path="/forgot-password" element={<ForgotPasswordPage />} />
          <Route path="/reset-password" element={<ResetPasswordForm />} />
          <Route path="/admin/users" element={<UserList />} />
          <Route path="/admin/add-user" element={<UserAddComponent />} />
          <Route
            path="/admin/edit-user/:userName"
            element={<UserEditComponent />}
          />

          {/* <Route path="/summercamp" element={<SummerCampPage />} /> */}
          <Route path="/admission-enquiry" element={<AdmissionEnquiryPage />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
