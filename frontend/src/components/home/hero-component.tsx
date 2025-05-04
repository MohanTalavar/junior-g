import React, { useEffect, useRef, useState } from "react";

const images = [
  {
    src: "https://plus.unsplash.com/premium_photo-1663106419176-ab3e42499a5c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    alt: "Group Fun in Classroom",
  },
  {
    src: "https://plus.unsplash.com/premium_photo-1681842143575-03bf1be4c11c?q=80&w=2086&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    alt: "Children Engaged in Creative Activities",
  },
  {
    src: "https://images.unsplash.com/photo-1587616211892-f743fcca64f9?q=80&w=2075&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    alt: "Exploring the World",
  },
  {
    src: "https://plus.unsplash.com/premium_photo-1661373619731-0d4ac1774f21?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
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
      setIndex((prevIndex) => prevIndex + 1);
    }, 3000); // change slide every 3 seconds
    return () => resetTimeout();
  }, [index]);

  useEffect(() => {
    if (index === totalSlides) {
      // When showing the duplicate (clone), after transition, jump to first real image instantly
      const timer = setTimeout(() => {
        setIsTransitioning(false); // disable transition
        setIndex(0);
      }, 1000); // must match the transition duration

      return () => clearTimeout(timer);
    } else {
      setIsTransitioning(true); // re-enable transition when moving normally
    }
  }, [index, totalSlides]);

  return (
    <section className="w-full h-[600px] overflow-hidden relative">
      <div
        className={`flex ${
          isTransitioning
            ? "transition-transform duration-1000 ease-in-out"
            : ""
        }`}
        style={{ transform: `translateX(-${index * 100}%)` }}
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
