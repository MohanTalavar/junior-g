import React, { useEffect, useRef, useState } from "react";

const images = [
  {
    src: "https://plus.unsplash.com/premium_photo-1663106419176-ab3e42499a5c?q=80&w=2070&auto=format&fit=crop",
    alt: "Group Fun in Classroom",
  },
  {
    src: "https://plus.unsplash.com/premium_photo-1681842143575-03bf1be4c11c?q=80&w=2086&auto=format&fit=crop",
    alt: "Children Engaged in Creative Activities",
  },
  {
    src: "https://images.unsplash.com/photo-1587616211892-f743fcca64f9?q=80&w=2075&auto=format&fit=crop",
    alt: "Exploring the World",
  },
  {
    src: "https://plus.unsplash.com/premium_photo-1661373619731-0d4ac1774f21?q=80&w=2070&auto=format&fit=crop",
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
