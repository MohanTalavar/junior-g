import InfoComponent from "@/components/home/info-component";
import HeroComponent from "@/components/home/hero-component";
import CourseComponent from "@/components/home/course-component";
import AboutUsComponent from "@/components/home/about-us-component";
import AnnouncementBar from "@/components/home/announcement-bar-component";
import {
  WhatsappFloat,
  FacebookFloat,
  InstagramFloat,
} from "@/components/ui/social-floaters";

const Home: React.FC = () => {
  return (
    <div className="flex flex-col">
      {/* Hero Section */}
      <HeroComponent />

      {/* Annoncement Bar */}

      <AnnouncementBar />

      {/* Junior G Info Section */}
      <InfoComponent />

      {/* Courses Section */}
      <CourseComponent />

      {/* About Us + Contact Section */}
      <AboutUsComponent />

      <WhatsappFloat />
      <FacebookFloat />
      <InstagramFloat />
      <footer className="w-full text-center p-4  text-white text-sm bg-[#002F6C]">
        © Copyright {new Date().getFullYear()} Junior-G International Pre School
        | Designed & Maintained by{" "}
        <a
          className="font-semibold  underline hover:text-gray-300 transition-colors"
          href="https://www.linkedin.com/in/mohan-talavar/"
        >
          Mohan Talavar{" "}
        </a>
      </footer>
      {/* <footer className="w-full text-center p-4 md:p-6 text-white text-sm bg-[#002F6C] border-t border-gray-200">
        <div className="space-y-1">
          <p>© {new Date().getFullYear()} Junior-G International Pre School</p>
          <p>
            Designed & Maintained by{" "}
            <a
              className="font-semibold underline hover:text-gray-300 transition-colors"
              href="https://www.linkedin.com/in/mohan-talavar/"
              target="_blank"
              rel="noopener noreferrer"
            >
              Mohan Talavar
            </a>
          </p>
        </div>
      </footer> */}
    </div>
  );
};

export default Home;
