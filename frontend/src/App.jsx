import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Navbar from "./components/Navbar/Navbar";
import TeacherPage from "./pages/TeacherPage";
import Logout from "./pages/Logout";
import CoursePage from "./pages/CoursePage";

const App = () => {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/login" element={<Login />} />        
        <Route path="/teacher" element={<TeacherPage />}></Route>
        <Route path="/logout" element={<Logout />} />
        <Route path="/course" element={<CoursePage />} />
      </Routes>
    </Router>
  );
};

export default App;
