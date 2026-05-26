import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  LoaderCircle,
  Phone,
  Mail,
  MapPin,
  BookOpen,
  Users,
  Shield,
  Rocket,
  Heart,
  Globe,
} from "lucide-react";
import { useState } from "react";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { useNavigate } from "react-router-dom";

const AboutUsComponent: React.FC = () => {
  const [mapLoaded, setMapLoaded] = useState(false);
  const navigate = useNavigate();

  const handleAdmissionClick = () => {
    navigate("/admission-enquiry");
  };

  const features = [
    {
      icon: BookOpen,
      title: "CBSE Excellence",
      description:
        "Rigorous, nationally recognized curriculum that builds strong academic foundations",
    },
    {
      icon: Users,
      title: "Certified Teachers",
      description:
        "Qualified, caring educators dedicated to nurturing every child's potential",
    },
    {
      icon: Shield,
      title: "Safe Environment",
      description:
        "Modern facilities with child-centric approach and comprehensive safety measures",
    },
    {
      icon: Rocket,
      title: "Holistic Growth",
      description:
        "Beyond academics: robotics, trips, creative projects, and hands-on learning",
    },
    {
      icon: Heart,
      title: "Personalized Care",
      description:
        "Small batches ensuring individual attention and tailored learning experiences",
    },
    {
      icon: Globe,
      title: "Community Focus",
      description:
        "Regular parent involvement, transparent communication, and engaged partnerships",
    },
  ];

  return (
    <section className="w-full py-20 bg-gradient-to-b from-[#f1f1f1] to-white">
      <div className="max-w-7xl mx-auto px-4">
        {/* Hero Section */}
        <div className="mb-16 text-center">
          <h2 className="text-4xl md:text-5xl font-bold text-[#7B1E3A] mb-4">
            Why Choose Junior G?
          </h2>
          <p className="text-lg text-gray-600 max-w-3xl mx-auto leading-relaxed">
            We believe every child is unique. At Junior G, we nurture holistic
            development through quality education, safe environments, and
            genuine care for each student's growth.
          </p>
        </div>

        {/* Features Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-20">
          {features.map((feature, index) => {
            const IconComponent = feature.icon;
            return (
              <Card
                key={index}
                className="border-0 shadow-md hover:shadow-lg transition-all duration-300 hover:scale-105 bg-white overflow-hidden group"
              >
                <div className="h-1 bg-gradient-to-r from-[#7B1E3A] to-[#8B0000]"></div>
                <CardHeader className="pb-3">
                  <div className="mb-3">
                    <IconComponent className="w-8 h-8 text-[#7B1E3A] group-hover:text-[#8B0000] transition-colors" />
                  </div>
                  <CardTitle className="text-lg font-semibold text-[#7B1E3A]">
                    {feature.title}
                  </CardTitle>
                </CardHeader>
                <CardContent>
                  <CardDescription className="text-sm text-gray-600 leading-relaxed">
                    {feature.description}
                  </CardDescription>
                </CardContent>
              </Card>
            );
          })}
        </div>

        {/* Enhanced Three-Column Section */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          {/* Column 1: About Junior G */}
          <Card className="border-0 shadow-lg bg-white">
            <CardHeader>
              <CardTitle className="text-2xl text-[#7B1E3A]">
                About Junior G
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <p className="text-gray-700 leading-relaxed text-sm">
                Established with a passion for early childhood education, Junior
                G provides a nurturing space where children learn, explore, and
                grow. We combine CBSE curriculum excellence with creative
                expression and practical life skills.
              </p>
              <Dialog>
                <DialogTrigger asChild>
                  <Button className="w-full bg-[#F7B801] hover:bg-[#F18701] text-white rounded-lg font-semibold">
                    Schedule Campus Visit
                  </Button>
                </DialogTrigger>
                <DialogContent className="max-w-sm bg-white text-gray-800 rounded-xl shadow-lg">
                  <DialogHeader>
                    <DialogTitle className="text-xl font-semibold text-[#8B0000]">
                      Get in Touch with Junior G
                    </DialogTitle>
                  </DialogHeader>
                  <div className="flex flex-col gap-4 mt-4">
                    <Button
                      onClick={() => (window.location.href = "tel:7719005081")}
                      className="bg-[#8B0000] hover:bg-red-800 text-white rounded-lg hover:cursor-pointer"
                    >
                      📞 Call Us
                    </Button>
                    <Button
                      onClick={handleAdmissionClick}
                      className="bg-[#F7B801] hover:bg-[#F18701] text-white rounded-lg hover:cursor-pointer"
                    >
                      📝 Admission Enquiry
                    </Button>
                  </div>
                </DialogContent>
              </Dialog>
            </CardContent>
          </Card>

          {/* Column 2: Contact Us */}
          <Card className="border-0 shadow-lg bg-white">
            <CardHeader>
              <CardTitle className="text-2xl text-[#7B1E3A]">
                Contact Us
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="space-y-3">
                {["9011251084", "7719005081", "7350629758"].map((phone) => (
                  <a
                    key={phone}
                    href={`tel:${phone}`}
                    className="flex items-center gap-3 text-gray-700 hover:text-[#7B1E3A] transition-colors group cursor-pointer"
                  >
                    <Phone className="w-5 h-5 text-[#7B1E3A] group-hover:scale-110 transition-transform" />
                    <span className="text-sm font-medium">{phone}</span>
                  </a>
                ))}
              </div>
              <div className="pt-2 border-t border-gray-200">
                <a
                  href="mailto:info@juniorg.com"
                  className="flex items-center gap-3 text-gray-700 hover:text-[#7B1E3A] transition-colors group cursor-pointer"
                >
                  <Mail className="w-5 h-5 text-[#7B1E3A] group-hover:scale-110 transition-transform" />
                  <span className="text-sm font-medium">info@juniorg.com</span>
                </a>
              </div>
            </CardContent>
          </Card>

          {/* Column 3: Find Us */}
          <Card className="border-0 shadow-lg bg-white">
            <CardHeader>
              <CardTitle className="text-2xl text-[#7B1E3A]">Find Us</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="flex gap-3">
                <MapPin className="w-5 h-5 text-[#7B1E3A] flex-shrink-0 mt-1" />
                <p className="text-gray-700 text-sm leading-relaxed">
                  Plot No.145, Sec.No.20, Krishnanagar, Near Hanuman Mandir,
                  Chinchwad, Pune - 19
                </p>
              </div>

              {/* Map with loader */}
              <div className="relative w-full h-64 rounded-lg overflow-hidden mt-4 border border-gray-200">
                {!mapLoaded && (
                  <div className="absolute inset-0 flex justify-center items-center bg-gray-100 z-10">
                    <LoaderCircle className="h-8 w-8 animate-spin text-[#7B1E3A]" />
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
            </CardContent>
          </Card>
        </div>
      </div>
    </section>
  );
};

export default AboutUsComponent;
