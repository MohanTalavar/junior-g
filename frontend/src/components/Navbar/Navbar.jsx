import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Navbar.css"; // Importing the CSS file for styles

const Navbar = () => {

  const navigte = useNavigate();

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/home" className="navbar-link">Junior G</Link>
        <div className="navbar-nav-items">
          <Link to="/home" className="navbar-link">Home</Link>
          <Link to="/teacher" className="navbar-link">Teachers</Link>
          <Link to="/student" className="navbar-link">Students</Link>
          <Link to="/profile" className="navbar-link">Profile</Link>
          <Link to="/logout" className="navbar-link">Logout</Link>

        </div>
      </div>
    </nav>
  );
};

export default Navbar;
