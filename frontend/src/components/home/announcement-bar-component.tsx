// src/components/home/announcement-bar.tsx
import { useState } from "react";

const messages = [
  "🎓 Admissions are open for the academic year 2026–2027. Apply early to reserve your child’s seat!",
  "📞 Enquire Now – Limited seats available across all programs. Call us or visit the Admission Enquiry Page!",
];

export const AnnouncementBar = () => {
  const [isHovered, setIsHovered] = useState(false);
  const repeated = [...messages, ...messages];

  return (
    <div
      className="w-full bg-[#8B0000] text-white h-[1.5cm] overflow-hidden flex items-center"
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div
        className={`w-max flex whitespace-nowrap px-4 text-sm font-medium tracking-wide [will-change:transform] ${
          !isHovered ? "animate-marquee" : ""
        }`}
      >
        {repeated.map((msg, idx) => (
          <span key={idx} className="mx-8">
            {msg}
          </span>
        ))}
      </div>
    </div>
  );
};

export default AnnouncementBar;
