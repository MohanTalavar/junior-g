const InfoComponent: React.FC = () => {
  return (
    <div>
      {/* Junior G Info Section */}
      <section className="py-4 bg-gradient-to-r from-[#FFD8A9] to-[#F9F4FF]">
        <div className="max-w-6xl mx-auto text-center px-4">
          <h2 className="text-5xl font-bold text-[#FF7B00] mb-4">
            Welcome to Junior G International Pre-School
          </h2>
          <p className="text-base text-orange-700 font-medium leading-relaxed">
            <span className="font-semibold">
              Junior G International Pre-School
            </span>{" "}
            provides a nurturing and vibrant early learning environment. Our{" "}
            <span className="font-semibold">certified teachers</span> and{" "}
            <span className="font-semibold">CBSE-aligned curriculum</span> focus
            on holistic development through playful, hands-on learning
            experiences. We ensure a <span className="font-semibold">safe</span>
            , <span className="font-semibold">interactive</span> atmosphere that
            fosters <span className="italic">curiosity</span>,{" "}
            <span className="italic">creativity</span>, and{" "}
            <span className="italic">confidence</span> in every child.
          </p>
        </div>
      </section>
    </div>
  );
};

export default InfoComponent;
