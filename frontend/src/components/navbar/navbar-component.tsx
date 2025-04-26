import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../app/store";
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"; // Shadcn Sheet
import { Menu } from "lucide-react"; // hamburger icon

const Navbar: React.FC = () => {
  const isAuthenticated = useSelector(
    (state: RootState) => state.auth.isAuthenticated
  );

  return (
    <nav className="bg-[#FFA500] p-4 shadow-md">
      <div className="flex justify-between items-center">
        {/* Left Logo */}
        <Link
          to="/home"
          className="text-white text-2xl font-bold hover:underline hover:underline-offset-4"
        >
          Junior G
        </Link>

        {/* Desktop Nav Links */}
        <div className="hidden md:flex gap-6">
          <Link
            to="/home"
            className="text-white text-lg font-semibold hover:underline hover:underline-offset-4"
          >
            Home
          </Link>
          <Link
            to="/course"
            className="text-white text-lg font-semibold hover:underline hover:underline-offset-4"
          >
            Courses
          </Link>

          {isAuthenticated && (
            <>
              <Link
                to="/teacher"
                className="text-white text-lg font-semibold hover:underline hover:underline-offset-4"
              >
                Teachers
              </Link>
              <Link
                to="/student"
                className="text-white text-lg font-semibold hover:underline hover:underline-offset-4"
              >
                Students
              </Link>
            </>
          )}

          <Link
            to="/profile"
            className="text-white text-lg font-semibold hover:underline hover:underline-offset-4"
          >
            Profile
          </Link>

          {isAuthenticated ? (
            <Link
              to="/logout"
              className="text-white text-lg font-semibold hover:underline hover:underline-offset-4"
            >
              Logout
            </Link>
          ) : (
            <Link
              to="/login"
              className="text-white text-lg font-semibold hover:underline hover:underline-offset-4"
            >
              Login
            </Link>
          )}
        </div>

        {/* Mobile Hamburger Menu */}
        <div className="md:hidden">
          <Sheet>
            <SheetTrigger>
              <Menu className="text-white" size={32} />
            </SheetTrigger>

            <SheetContent side="right" className="bg-white w-[250px] p-6">
              <div className="flex flex-col gap-6 mt-12 text-lg">
                <Link
                  to="/home"
                  className="text-[#333333] hover:text-[#FF7B00] hover:underline hover:underline-offset-4 transition-all duration-300 ease-in-out hover:scale-105"
                >
                  Home
                </Link>
                <Link
                  to="/course"
                  className="text-[#333333] hover:text-[#FF7B00] hover:underline hover:underline-offset-4 transition-all duration-300 ease-in-out hover:scale-105 delay-100"
                >
                  Courses
                </Link>

                {isAuthenticated && (
                  <>
                    <Link
                      to="/teacher"
                      className="text-[#333333] hover:text-[#FF7B00] hover:underline hover:underline-offset-4 transition-all duration-300 ease-in-out hover:scale-105 delay-200"
                    >
                      Teachers
                    </Link>
                    <Link
                      to="/student"
                      className="text-[#333333] hover:text-[#FF7B00] hover:underline hover:underline-offset-4 transition-all duration-300 ease-in-out hover:scale-105 delay-300"
                    >
                      Students
                    </Link>
                  </>
                )}

                <Link
                  to="/profile"
                  className="text-[#333333] hover:text-[#FF7B00] hover:underline hover:underline-offset-4 transition-all duration-300 ease-in-out hover:scale-105 delay-400"
                >
                  Profile
                </Link>

                {isAuthenticated ? (
                  <Link
                    to="/logout"
                    className="text-[#333333] hover:text-[#FF7B00] hover:underline hover:underline-offset-4 transition-all duration-300 ease-in-out hover:scale-105 delay-500"
                  >
                    Logout
                  </Link>
                ) : (
                  <Link
                    to="/login"
                    className="text-[#333333] hover:text-[#FF7B00] hover:underline hover:underline-offset-4 transition-all duration-300 ease-in-out hover:scale-105 delay-500"
                  >
                    Login
                  </Link>
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
