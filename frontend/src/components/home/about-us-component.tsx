import { Button } from "@/components/ui/button";

const AboutUsComponent: React.FC = () => {
  return (
    <div>
      {/* About Us + Contact Section */}
      <section className="py-16 bg-gradient-to-r from-[#FFD8A9] to-[#F9F4FF]">
        <div className="max-w-7xl mx-auto px-4 grid grid-cols-1 md:grid-cols-2 gap-12">
          {/* Left Section */}
          <div>
            <h3 className="text-2xl font-bold text-[#3d348b] mb-6">About Us</h3>
            <ul className="space-y-4 text-gray-700 list-disc list-inside">
              <li>CBSE Based Learning</li>
              <li>Certified Teachers</li>
              <li>Interactive, Safe Atmosphere</li>
              <li>Projects, Trips, Robotics Kits</li>
              <li>Surprise Activities and Mall Visits</li>
            </ul>
            <Button className="mt-8 bg-[#f18701] hover:bg-[#f35b04] text-white border border-[#f35b04] rounded-lg shadow-lg hover:shadow-xl transition-all duration-300">
              Learn More
            </Button>
          </div>

          {/* Right Section - Map */}
          <div>
            <h3 className="text-2xl font-bold text-[#3d348b] mb-6">Find Us</h3>
            <p className="mb-4 text-gray-600">
              Plot No.145, Sec.No.20, Krishnanagar, Near Hanuman Mandir,
              Chinchwad, Pune - 19
            </p>
            <iframe
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3779.9370033037253!2d73.7912139!3d18.6668227!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3bc2b750a969751f%3A0x5579bda2a737a3f2!2sJunior%20G%20International%20Preschool!5e0!3m2!1sen!2sin!4v1745678335439!5m2!1sen!2sin"
              className="w-full h-72 rounded-lg border-0 shadow-lg"
              allowFullScreen
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
            />
          </div>
        </div>
      </section>
    </div>
  );
};

export default AboutUsComponent;
