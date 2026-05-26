import InfoComponent from "@/components/home/info-component";
import HeroComponent from "@/components/home/hero-component";
import CourseComponent from "@/components/home/course-component";
import TestimonialComponent from "@/components/home/testimonial-component";
import AboutUsComponent from "@/components/home/about-us-component";
import AnnouncementBar from "@/components/home/announcement-bar-component";
import {
  WhatsappFloat,
  FacebookFloat,
  InstagramFloat,
} from "@/components/ui/social-floaters";
import { APP_VERSION } from "@/version";
import { testimonials } from "@/data/testimonials";

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

      {/* Testimonials Section */}
      <TestimonialComponent testimonials={testimonials} />

      {/* About Us + Contact Section */}
      <AboutUsComponent />

      <WhatsappFloat />
      <FacebookFloat />
      <InstagramFloat />

      <footer className="w-full bg-[#002F6C] text-white text-sm">
        <div className="max-w-5xl mx-auto px-4 py-3 flex flex-col items-center gap-2">
          {/* Top: Copyright */}
          <div className="text-center text-xs opacity-90">
            © {new Date().getFullYear()} Junior-G International Pre School |
            Designed & Maintained by{" "}
            <span className="font-semibold">Mohan Talavar</span>
          </div>

          {/* Divider */}
          <div className="w-full max-w-md border-t border-white/20" />

          {/* Bottom: Release + Connect */}
          <div className="flex flex-wrap items-center justify-center gap-6 text-xs">
            {/* Release */}
            <div className="flex items-center gap-2">
              <span className="uppercase tracking-wide opacity-80">
                Release
              </span>

              <a
                href="https://github.com/MohanTalavar/junior-g/releases/tag/v1.4.1"
                target="_blank"
                rel="noopener noreferrer"
                className="flex items-center gap-1 rounded-full bg-white/10 px-2 py-0.5
                        hover:bg-white/20 transition"
                title="View release notes"
              >
                <span className="font-medium">{APP_VERSION}</span>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-3 w-3"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                  strokeWidth={2}
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    d="M14 3h7m0 0v7m0-7L10 14"
                  />
                </svg>
              </a>
            </div>

            {/* Soft vertical separator */}
            <div className="h-4 w-px bg-white/30" />

            {/* Connect with me */}
            <div className="flex items-center gap-3">
              <span className="uppercase tracking-wide opacity-80">
                Connect with me
              </span>

              {/* LinkedIn */}
              <a
                href="https://www.linkedin.com/in/mohan-talavar"
                target="_blank"
                rel="noopener noreferrer"
                className="hover:text-blue-400 transition"
                title="LinkedIn"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  viewBox="0 0 24 24"
                  fill="currentColor"
                  className="h-4 w-4"
                >
                  <path d="M4.98 3.5C4.98 4.88 3.88 6 2.5 6S0 4.88 0 3.5 1.12 1 2.5 1 4.98 2.12 4.98 3.5zM0 8h5v16H0V8zm7.5 0h4.8v2.2h.1c.7-1.3 2.4-2.7 5-2.7 5.3 0 6.3 3.5 6.3 8v8.5h-5V16c0-1.9 0-4.4-2.7-4.4-2.7 0-3.1 2.1-3.1 4.3v8.1h-5V8z" />
                </svg>
              </a>

              {/* Email */}
              <a
                href="https://mail.google.com/mail/?view=cm&fs=1&to=mohan.talawar.20@gmail.com"
                target="_blank"
                rel="noopener noreferrer"
                className="hover:text-green-400 transition"
                title="Send email via Gmail"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="currentColor"
                  viewBox="0 0 24 24"
                  className="h-5 w-5"
                >
                  <path d="M20 4H4a2 2 0 00-2 2v12a2 2 0 002 2h16a2 2 0 002-2V6a2 2 0 00-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z" />
                </svg>
              </a>
            </div>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Home;
