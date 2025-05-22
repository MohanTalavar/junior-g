import { Link } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../app/store";
import {
  Sheet,
  SheetContent,
  SheetTrigger,
  SheetTitle,
  SheetDescription,
} from "@/components/ui/sheet";
import { Menu } from "lucide-react";
import crestImg from "../../assets/images/JuniorG_Crest.png";

const Navbar: React.FC = () => {
  const isAuthenticated = useSelector(
    (state: RootState) => state.auth.isAuthenticated
  );

  return (
    <nav className="bg-white p-2 md:p-4 py-2 shadow-md ">
      <div className="flex justify-between items-center">
        {/* Logo + Brand Block */}
        <div className="flex items-center gap-6 md:pl-8 pl-2">
          <Link
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
          </Link>

          {/* Summer Camp link */}
          {/* <Link
            to="/summercamp"
            className="text-[#F18701] mt-4 md:mt-9 font-bold text-xs mr-2 md:text-xl font-serif uppercase tracking-wider hover:text-[#f35b04] transition duration-300"
          >
            Summer Camp!
          </Link> */}

          {/* Admission Enquiry link */}
          <div className="hidden md:block md:mt-9">
            <Link
              to="/admission-enquiry"
              className="text-[#002F6C] mt-4 font-semibold text-[4px] mr-2 md:text-xl font-serif uppercase tracking-wider
               hover:text-[#004D99] hover:underline hover:underline-offset-6 transition duration-300"
            >
              Admission Enquiry!
            </Link>
          </div>
        </div>

        {/* Desktop Navigation */}
        <div className="hidden md:flex gap-6">
          {isAuthenticated && (
            <>
              <Link
                to="/teacher"
                className="text-[#002F6C]  md:mt-9 font-semibold font-serif text-xl hover:text-[#004D99] transition duration-300 hover:underline hover:underline-offset-6"
              >
                Teachers
              </Link>
              <Link
                to="/student"
                className="text-[#002F6C] md:mt-9 font-semibold font-serif text-xl hover:text-[#004D99] transition duration-300 hover:underline hover:underline-offset-6"
              >
                Students
              </Link>
            </>
          )}
          {isAuthenticated ? (
            <Link
              to="/logout"
              className="text-[#002F6C] md:mt-9 font-semibold font-serif text-xl hover:text-[#004D99] transition duration-300 mr-4 hover:underline hover:underline-offset-6"
            >
              Logout
            </Link>
          ) : (
            <Link
              to="/login"
              className="text-[#002F6C] font-bold font-serif text-xl hover:text-[#004D99] transition duration-300 mr-4 mt-9 hover:underline hover:underline-offset-6"
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
              <SheetDescription id="mobile-nav-description"></SheetDescription>

              <div className="flex flex-col gap-4 text-base font-serif">
                {/* <Link
                  to="/summercamp"
                  className="font-semibold text-[#F18701] hover:text-[#f35b04] transition duration-300"
                >
                  Summer Camp 2025
                </Link> */}

                <Link
                  to="/admission-enquiry"
                  className="font-semibold text-[#F18701] hover:text-[#f35b04] transition duration-300"
                >
                  Admission Enquiry
                </Link>

                {isAuthenticated && (
                  <>
                    <Link
                      to="/teacher"
                      className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                    >
                      Teachers
                    </Link>
                    <Link
                      to="/student"
                      className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                    >
                      Students
                    </Link>
                  </>
                )}
                {isAuthenticated ? (
                  <Link
                    to="/logout"
                    className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
                  >
                    Logout
                  </Link>
                ) : (
                  <Link
                    to="/login"
                    className="font-medium text-[#002F6C] hover:text-[#004D99] transition duration-300"
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
