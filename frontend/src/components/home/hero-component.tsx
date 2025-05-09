import React, { useEffect, useRef, useState } from "react";
import heroImg1 from "../../assets/images/HeroImg1.png";
import heroImg2 from "../../assets/images/HeroImg2.png";
import heroImg3 from "../../assets/images/HeroImg3.png";
import heroImg4 from "../../assets/images/HeroImg4.png";

const images = [
  {
    src: heroImg1,
    alt: "Group Fun in Classroom",
  },
  {
    src: heroImg2,
    alt: "Children Engaged in Creative Activities",
  },
  {
    src: heroImg3,
    alt: "Exploring the World",
  },
  {
    src: heroImg4,
    alt: "Creative Playtime",
  },
];

const HeroComponent: React.FC = () => {
  const [index, setIndex] = useState(0);
  const [isTransitioning, setIsTransitioning] = useState(true);
  const timeoutRef = useRef<NodeJS.Timeout | null>(null);
  const totalSlides = images.length;

  const resetTimeout = () => {
    if (timeoutRef.current) clearTimeout(timeoutRef.current);
  };

  useEffect(() => {
    resetTimeout();
    timeoutRef.current = setTimeout(() => {
      setIndex((prevIndex) => (prevIndex + 1) % (totalSlides + 1));
    }, 3500);
    return () => resetTimeout();
  }, [index]);

  const handleTransitionEnd = () => {
    if (index === totalSlides) {
      // Jump to real image[0] without animation
      setIsTransitioning(false);
      setIndex(0);
    }
  };

  useEffect(() => {
    if (!isTransitioning) {
      // Wait one render frame before reenabling transition
      const id = requestAnimationFrame(() => setIsTransitioning(true));
      return () => cancelAnimationFrame(id);
    }
  }, [isTransitioning]);

  return (
    <section className="w-full h-[600px] overflow-hidden relative">
      <div
        className={`flex ${
          isTransitioning
            ? "transition-transform duration-1000 ease-in-out"
            : ""
        }`}
        style={{ transform: `translateX(-${index * 100}%)` }}
        onTransitionEnd={handleTransitionEnd}
      >
        {[...images, images[0]].map((img, i) => (
          <img
            key={i}
            src={img.src}
            alt={img.alt}
            className="w-full h-[600px] object-cover flex-shrink-0"
          />
        ))}
      </div>
    </section>
  );
};

export default HeroComponent;
