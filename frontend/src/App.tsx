import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/navbar/navbar-component";
import Home from "./pages/home-page";
import Login from "./pages/login-page";
import TeacherPage from "./pages/teacher-page";
import Logout from "./pages/logout-page";
import CoursePage from "./pages/CoursePage";
import SummerCampPage from "./pages/summer-camp-page";

function App() {
  return (
    <>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/teacher" element={<TeacherPage />}></Route>
          <Route path="/logout" element={<Logout />} />
          <Route path="/course" element={<CoursePage />} />
          <Route path="summercamp" element={<SummerCampPage />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
