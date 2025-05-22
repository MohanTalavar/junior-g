// src/components/home/announcement-bar.tsx

const messages = [
  "🎓 Admissions are open for the academic year 2025–2026. Apply early to reserve your child’s seat!",
  "📞 Enquire Now – Limited seats available across all programs. Call us or visit the Admission Enquiry Page!",
];

export const AnnouncementBar = () => {
  // duplicate for seamless loop
  const repeated = [...messages, ...messages];

  return (
    <div className="w-full bg-[#8B0000] text-white h-[1.5cm] overflow-hidden flex items-center">
      <div className="w-max flex whitespace-nowrap animate-marquee px-4 text-sm font-medium tracking-wide [will-change:transform]">
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
