import React from "react";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel";

const HeroComponent: React.FC = () => {
  return (
    <div>
      {/* Hero Section */}
      <section className="bg-gradient-to-r from-[#FFD8A9] to-[#F9F4FF]">
        <div className="max-w-7xl mx-auto px-4">
          <Carousel>
            <CarouselContent>
              {[
                {
                  src: "https://images.pexels.com/photos/8613086/pexels-photo-8613086.jpeg",
                  alt: "Storytime at Preschool",
                },
                {
                  src: "https://images.pexels.com/photos/8612925/pexels-photo-8612925.jpeg",
                  alt: "Group Fun",
                },
                {
                  src: "https://images.pexels.com/photos/8613080/pexels-photo-8613080.jpeg",
                  alt: "Exploring the World",
                },
                {
                  src: "https://images.pexels.com/photos/8612930/pexels-photo-8612930.jpeg",
                  alt: "Creative Play",
                },
              ].map((item, idx) => (
                <CarouselItem key={idx}>
                  <img
                    src={item.src}
                    alt={item.alt}
                    className="w-full h-96 object-cover rounded-2xl shadow-lg"
                  />
                </CarouselItem>
              ))}
            </CarouselContent>
            <CarouselPrevious />
            <CarouselNext />
          </Carousel>
        </div>
      </section>
    </div>
  );
};

export default HeroComponent;
