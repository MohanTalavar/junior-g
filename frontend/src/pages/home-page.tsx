import InfoComponent from "@/components/home/info-component";
import HeroComponent from "@/components/home/hero-component";
import CourseComponent from "@/components/home/course-component";
import AboutUsComponent from "@/components/home/about-us-component";

const Home: React.FC = () => {
  return (
    <div className="flex flex-col">
      {/* Junior G Info Section */}
      <InfoComponent />

      {/* Hero Section */}
      <HeroComponent />

      {/* Courses Section */}
      <CourseComponent />

      {/* About Us + Contact Section */}
      <AboutUsComponent />
    </div>
  );
};

export default Home;
