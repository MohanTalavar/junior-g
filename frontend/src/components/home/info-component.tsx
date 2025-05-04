import React from "react";
import crestImg from "../../assets/images/JuniorG_Crest.png";

const InfoComponent: React.FC = () => {
  return (
    <section className="w-full bg-[#002F6C] text-white">
      <div className="flex flex-col md:flex-row w-full min-h-[450px]">
        {/* Left Column */}
        <div className="w-full md:w-[60%] px-8 py-12 flex flex-col justify-center">
          <h2 className="text-4xl md:text-5xl font-bold mb-8 text-center md:text-left">
            About Junior G
          </h2>
          <p className="text-lg font-medium leading-relaxed md:text-left">
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

        {/* Right Column */}
        <div className="hidden md:flex bg-[#f1f1f1] w-full md:w-[40%] items-center justify-center p-8">
          <img src={crestImg} alt="Junior G Crest" className="w-[70%] h-auto" />
        </div>
      </div>
    </section>
  );
};

export default InfoComponent;
