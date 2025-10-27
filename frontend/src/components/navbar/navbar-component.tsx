// src/components/navbar/navbar-component.tsx
import {
  Sheet,
  SheetContent,
  SheetDescription,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { Menu } from "lucide-react";
import React, { useState } from "react";
import { useSelector } from "react-redux";
import { NavLink } from "react-router-dom";
import { RootState } from "../../app/store";
import crestImg from "../../assets/images/JuniorG_Crest.png";

const Navbar: React.FC = () => {
  // pull user and role out of Redux
  const { isAuthenticated, user, role } = useSelector(
    (state: RootState) => state.auth
  );
  const [open, setOpen] = useState(false);

  return (
    <nav className="bg-white p-2 md:p-4 shadow-md">
      <div className="flex justify-between items-center">
        {/* Logo + Brand */}
        <div className="flex items-center gap-6 md:pl-8 pl-2">
          <NavLink
            to="/"
            className="flex items-center text-[#002F6C] text-2xl font-serif font-bold tracking-wide hover:text-[#004D99] transition duration-300"
          >
            <img src={crestImg} alt="Junior G Crest" className="h-20 mr-2" />
            <span className="uppercase text-sm md:text-3xl leading-tight">
              <div>JUNIOR G</div>
              <div className="text-xs md:text-sm mt-1">
                International Preschool
              </div>
            </span>
          </NavLink>
          <div className="hidden md:block md:mt-9">
            <NavLink
              to="/admission-enquiry"
              className="text-[#002F6C] mt-4 font-semibold text-[4px] mr-2 md:text-xl font-serif uppercase tracking-wider hover:text-[#004D99] hover:underline hover:underline-offset-6 transition duration-300"
            >
              Admission Enquiry!
            </NavLink>
          </div>
        </div>

        {/* Desktop Navigation */}
        <div className="hidden md:flex items-center gap-6">
          {isAuthenticated && role === "ROLE_ADMIN" && (
            <NavLink
              to="/admin/users"
              className="text-[#002F6C] md:mt-9 font-semibold font-serif text-xl hover:text-[#004D99] transition duration-300 hover:underline hover:underline-offset-6"
            >
              User Maintenance
            </NavLink>
          )}

          {isAuthenticated && role === "ROLE_ADMIN" && (
            <NavLink
              to="/teacher"
              className="text-[#002F6C] md:mt-9 font-semibold font-serif text-xl hover:text-[#004D99] transition duration-300 hover:underline hover:underline-offset-6"
            >
              Teachers
            </NavLink>
          )}

          {isAuthenticated && (
            <NavLink
              to="/student"
              className="text-[#002F6C] md:mt-9 font-semibold font-serif text-xl hover:text-[#004D99] transition duration-300 hover:underline hover:underline-offset-6"
            >
              Students
            </NavLink>
          )}
          {isAuthenticated ? (
            // Wrap user+logout in a column
            <div className="flex flex-col items-center">
              <span className="text-[#8B0000] font-medium mb-1">
                {user} ({role?.replace("ROLE_", "")})
              </span>
              <NavLink
                to="/logout"
                className="text-[#002F6C] font-semibold font-serif text-xl hover:text-[#004D99] transition duration-300 hover:underline hover:underline-offset-6"
              >
                Logout
              </NavLink>
            </div>
          ) : (
            <NavLink
              to="/login"
              className="text-[#002F6C] font-bold font-serif text-xl hover:text-[#004D99] transition duration-300 hover:underline hover:underline-offset-6 mt-9"
            >
              Login
            </NavLink>
          )}
        </div>

        {/* Mobile Nav (Sheet) */}
        <div className="md:hidden">
          <Sheet open={open} onOpenChange={setOpen}>
            <SheetTrigger>
              <Menu className="text-[#1C2C5B]" size={32} />
            </SheetTrigger>
            <SheetContent
              side="right"
              className="bg-gray-50 w-[250px] p-6"
              aria-labelledby="mobile-nav-title"
              aria-describedby="mobile-nav-description"
            >
              <SheetTitle
                id="mobile-nav-title"
                className="text-[#002F6C] font-bold text-xl font-serif mb-2 mt-6"
              >
                Menu
              </SheetTitle>
              <SheetDescription id="mobile-nav-description" />

              {/* display user + role */}
              {isAuthenticated && (
                <div className="mb-4 text-gray-700 font-medium">
                  {user} ({role?.replace("ROLE_", "")})
                </div>
              )}

              <NavLink
                to="/"
                className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                onClick={() => setOpen(false)}
              >
                {({ isActive }) => (
                  <div className="flex">
                    {isActive ? (
                      <div className="h-6 w-1.5 bg-[#002F6C] mr-2"></div>
                    ) : (
                      <div className="pl-4"></div>
                    )}
                    Home
                  </div>
                )}
              </NavLink>

              <div className="flex flex-col gap-4 text-base font-serif">
                <NavLink
                  to="/admission-enquiry"
                  className="font-semibold text-[#F18701] hover:text-[#f35b04] transition duration-300"
                  onClick={() => setOpen(false)}
                >
                  {({ isActive }) => (
                    <div className="flex">
                      {isActive ? (
                        <div className="h-6 w-1.5 bg-[#F18701] mr-2"></div>
                      ) : (
                        <div className="pl-4"></div>
                      )}
                      Admission Enquiry
                    </div>
                  )}
                </NavLink>

                {isAuthenticated && role === "ROLE_ADMIN" && (
                  <NavLink
                    to="/teacher"
                    className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                    onClick={() => setOpen(false)}
                  >
                    {({ isActive }) => (
                      <div className="flex">
                        {isActive ? (
                          <div className="h-6 w-1.5 bg-[#002F6C] mr-2"></div>
                        ) : (
                          <div className="pl-4"></div>
                        )}
                        Teachers
                      </div>
                    )}
                  </NavLink>
                )}

                {isAuthenticated && (
                  <NavLink
                    to="/student"
                    className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                    onClick={() => setOpen(false)}
                  >
                    {({ isActive }) => (
                      <div className="flex">
                        {isActive ? (
                          <div className="h-6 w-1.5 bg-[#002F6C] mr-2"></div>
                        ) : (
                          <div className="pl-4"></div>
                        )}
                        Students
                      </div>
                    )}
                  </NavLink>
                )}

                {isAuthenticated && role === "ROLE_ADMIN" && (
                  <NavLink
                    to="/admin/users"
                    className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                    onClick={() => setOpen(false)}
                  >
                    {({ isActive }) => (
                      <div className="flex">
                        {isActive ? (
                          <div className="h-6 w-1.5 bg-[#002F6C] mr-2"></div>
                        ) : (
                          <div className="pl-4"></div>
                        )}
                        User Management
                      </div>
                    )}
                  </NavLink>
                )}

                {isAuthenticated ? (
                  <NavLink
                    to="/logout"
                    className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                    onClick={() => setOpen(false)}
                  >
                    {({ isActive }) => (
                      <div className="flex">
                        {isActive ? (
                          <div className="h-6 w-1.5 bg-[#002F6C] mr-2"></div>
                        ) : (
                          <div className="pl-4"></div>
                        )}
                        Logout
                      </div>
                    )}
                  </NavLink>
                ) : (
                  <NavLink
                    to="/login"
                    className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                    onClick={() => setOpen(false)}
                  >
                    {({ isActive }) => (
                      <div className="flex">
                        {isActive ? (
                          <div className="h-6 w-1.5 bg-[#002F6C] mr-2"></div>
                        ) : (
                          <div className="pl-4"></div>
                        )}
                        Login
                      </div>
                    )}
                  </NavLink>
                )}
              </div>
            </SheetContent>
          </Sheet>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
