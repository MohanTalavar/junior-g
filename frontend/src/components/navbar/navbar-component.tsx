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
        {/* Logo + Brand Block */}
        <div className="flex items-center gap-6 md:pl-8 pl-2">
          <Link
            to="/home"
            className="flex items-center text-[#002F6C] text-2xl font-serif font-bold tracking-wide hover:text-[#004D99] transition duration-300"
          >
            <img src={crestImg} alt="Junior G Crest" className="h-20 mr-2" />
            <span className="uppercase text-sm md:text-3xl leading-tight">
              <div>JUNIOR G</div>
              <div className="text-xs md:text-sm mt-1">
                International Preschool
              </div>
            </span>
          </Link>

          {/* Summer Camp link */}
          <Link
            to="/summercamp"
            className="text-[#F18701] mt-4 md:mt-9 font-bold  text-xs mr-2 md:text-xl font-serif uppercase tracking-wider hover:text-[#f35b04] transition duration-300"
          >
            Summer Camp!
          </Link>
        </div>

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
              className="text-[#1C2C5B] font-bold font-serif text-xl hover:text-[#004D99] transition duration-300 mr-4"
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
                {/* Summer Camp for mobile */}
                {/* <Link
                  to="/summercamp"
                  className="text-[#F18701]  font-semibold hover:text-[#f35b04] transition duration-300"
                >
                  Summer Camp 2025
                </Link> */}

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
