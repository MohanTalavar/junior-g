import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../app/store";
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet";
import { Menu } from "lucide-react";
import crestImg from "../../assets/images/JuniorG_Crest.png";

const Navbar: React.FC = () => {
  const isAuthenticated = useSelector(
    (state: RootState) => state.auth.isAuthenticated
  );

  return (
    <nav className="bg-white p-2 md:p-6 py-4 shadow-md">
      <div className="flex justify-between items-center">
        {/* Logo */}
        <Link
          to="/home"
          className="flex items-center text-[#002F6C] text-2xl font-serif font-bold tracking-wide hover:text-[#004D99] transition duration-300"
        >
          {/* Crest */}
          <img
            src={crestImg}
            alt="Junior G Crest"
            className="h-20 pl-1 md:pl-8 " // Increased image size and left padding
          />
          {/* Brand Name */}
          <span className="uppercase text-3xl ">
            <div>JUNIOR G</div>
            <div className="text-sm mt-1">International Preschool</div>
          </span>
        </Link>

        {/* Desktop Navigation */}
        <div className="hidden md:flex gap-6">
          {isAuthenticated && (
            <>
              <Link
                to="/teacher"
                className="text-[#1C2C5B] font-medium font-serif text-xl hover:text-[#004D99] transition duration-300"
              >
                Teachers
              </Link>
              <Link
                to="/student"
                className="text-[#1C2C5B] font-medium font-serif text-xl hover:text-[#004D99] transition duration-300"
              >
                Students
              </Link>
            </>
          )}
          {isAuthenticated ? (
            <Link
              to="/logout"
              className="text-[#1C2C5B] font-medium font-serif text-xl hover:text-[#004D99] transition duration-300 mr-4"
            >
              Logout
            </Link>
          ) : (
            <Link
              to="/login"
              className="text-[#1C2C5B] font-medium font-serif text-xl hover:text-[#004D99] transition duration-300 mr-4"
            >
              Login
            </Link>
          )}
        </div>

        {/* Mobile Nav (Sheet) */}
        <div className="md:hidden">
          <Sheet>
            <SheetTrigger>
              <Menu className="text-[#1C2C5B]" size={32} />
            </SheetTrigger>
            <SheetContent side="right" className="bg-white w-[250px] p-6">
              <div className="flex flex-col gap-6 mt-12 text-lg font-serif text-[#1C2C5B]">
                {isAuthenticated && (
                  <>
                    <Link
                      to="/teacher"
                      className="hover:text-[#004D99] transition duration-300"
                    >
                      Teachers
                    </Link>
                    <Link
                      to="/student"
                      className="hover:text-[#004D99] transition duration-300"
                    >
                      Students
                    </Link>
                  </>
                )}
                {isAuthenticated ? (
                  <Link
                    to="/logout"
                    className="hover:text-[#004D99] transition duration-300"
                  >
                    Logout
                  </Link>
                ) : (
                  <Link
                    to="/login"
                    className="hover:text-[#004D99] transition duration-300"
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
