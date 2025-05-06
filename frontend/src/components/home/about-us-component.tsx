import { Button } from "@/components/ui/button";
import { LoaderCircle, Phone } from "lucide-react";
import { useState } from "react";

const AboutUsComponent: React.FC = () => {
  const [mapLoaded, setMapLoaded] = useState(false);

  return (
    <footer className="bg-[#8B0000] text-white py-10">
      <div className="max-w-7xl mx-auto px-4 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 space-y-6 md:space-y-0">
        {/* About Us */}
        <div>
          <h3 className="text-3xl font-bold mb-6 pb-2">About Us</h3>
          <ul className="space-y-3 list-disc list-inside text-white/90">
            <li>CBSE Based Learning</li>
            <li>Certified Teachers</li>
            <li>Interactive, Safe Atmosphere</li>
            <li>Projects, Trips, Robotics Kits</li>
            <li>Surprise Activities and Mall Visits</li>
          </ul>
          <Button className="mt-6 bg-white text-[#8B0000] hover:bg-gray-100 shadow-md rounded-lg">
            Learn More
          </Button>
        </div>

        {/* Contact Us */}
        <div>
          <h3 className="text-3xl font-bold mb-6 pb-2">Contact Us</h3>
          <ul className="space-y-4 text-white/90">
            {["9011251084", "7719005081", "7350629758"].map((phone) => (
              <li key={phone} className="flex items-center gap-3">
                <Phone className="w-5 h-5" />
                <span>{phone}</span>
              </li>
            ))}
          </ul>
        </div>

        {/* Find Us */}
        <div>
          <h3 className="text-3xl font-bold mb-6 pb-2">Find Us</h3>
          <p className="mb-4 text-white/90 leading-relaxed">
            Plot No.145, Sec.No.20, Krishnanagar, Near Hanuman Mandir,
            Chinchwad, Pune - 19
          </p>

          {/* Map with loader */}
          <div className="relative w-full h-64 rounded-md overflow-hidden mt-4">
            {!mapLoaded && (
              <div className="absolute inset-0 flex justify-center items-center bg-[#8B0000]/80 z-10">
                <LoaderCircle className="h-10 w-10 animate-spin text-white" />
              </div>
            )}
            <iframe
              onLoad={() => setMapLoaded(true)}
              src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3779.9370033037253!2d73.7912139!3d18.6668227!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3bc2b750a969751f%3A0x5579bda2a737a3f2!2sJunior%20G%20International%20Preschool!5e0!3m2!1sen!2sin!4v1745678335439!5m2!1sen!2sin"
              className="w-full h-full border-0"
              allowFullScreen
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
            />
          </div>
        </div>
      </div>
    </footer>
  );
};

export default AboutUsComponent;
