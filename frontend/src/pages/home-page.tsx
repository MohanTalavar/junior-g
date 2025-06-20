import InfoComponent from "@/components/home/info-component";
import HeroComponent from "@/components/home/hero-component";
import CourseComponent from "@/components/home/course-component";
import AboutUsComponent from "@/components/home/about-us-component";
import AnnouncementBar from "@/components/home/announcement-bar-component";
import WhatsappFloat from "@/components/ui/whatsapp-float";

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
      <footer className="w-full text-center py-0.5 text-white text-sm bg-[#002F6C]">
        © {new Date().getFullYear()} Junior-G International. All rights
        reserved.
      </footer>
    </div>
  );
};

export default Home;
