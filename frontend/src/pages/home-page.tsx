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
import { APP_VERSION } from "@/version";

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
      <footer className="w-full text-center p-2  text-white text-sm bg-[#002F6C]">
        © Copyright {new Date().getFullYear()} Junior-G International Pre School{" "}
        | Designed & Maintained by{" "}
        <a
          className="font-semibold  underline hover:text-gray-300 transition-colors"
          href="https://www.linkedin.com/in/mohan-talavar/"
        >
          Mohan Talavar{" "}
        </a>
        <div className="text-xs opacity-80 mt-1">{APP_VERSION}</div>
      </footer>
    </div>
  );
};

export default Home;
