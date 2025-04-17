import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Navbar from "./components/Navbar/Navbar";
import TeacherPage from "./pages/TeacherPage";
import Logout from "./pages/Logout";

const App = () => {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/home" element={<Home />} />
        <Route path="/" element={<Home />} />
        <Route path="/teacher" element={<TeacherPage />}></Route>
        <Route path="/logout" element={<Logout />} />
      </Routes>
    </Router>
  );
};

export default App;
