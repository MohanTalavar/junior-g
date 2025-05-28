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

          {/* <Route path="/summercamp" element={<SummerCampPage />} /> */}
          <Route path="/admission-enquiry" element={<AdmissionEnquiryPage />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
