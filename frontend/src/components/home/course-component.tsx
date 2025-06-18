import { Card, CardContent, CardTitle } from "@/components/ui/card";
import DayCareSvg from "../../assets/images/DayCare.png";
import PlayGroupSvg from "../../assets/images/PlayGroup.png";
import NurserySvg from "../../assets/images/Nursery.png";
import LKGSvg from "../../assets/images/LKG.png";
import UKGSvg from "../../assets/images/UKG.png";

const programs = [
  {
    title: "Daycare",
    age: "2 – 10 years",
    description:
      "Safe, flexible care for working parents. Engaging environment with meals, naps, and play.",
    image: DayCareSvg,
  },
  // {
  //   title: "PreSchool",
  //   description:
  //     "Structured learning through stories, rhymes, and play to develop social and cognitive skills.",
  // },
  {
    title: "Play Group",
    age: "2 – 3 years",
    description:
      "Exploration-based activities for toddlers, focusing on motor skills and curiosity.",
    image: PlayGroupSvg,
  },
  {
    title: "Nursery",
    age: "3 – 4 years",
    description:
      "Hands-on learning to develop pre-writing, pre-math, and problem-solving skills.",
    image: NurserySvg,
  },
  {
    title: "LKG",
    age: "4 – 5 years",
    description:
      "Foundational education focusing on language, numbers, art, and social interaction.",
    image: LKGSvg,
  },
  {
    title: "UKG",
    age: "5 – 6 years",
    description:
      "Advanced prep for Grade 1 with reading, writing, math, and concept-based learning.",
    image: UKGSvg,
  },
];

const CourseComponent: React.FC = () => {
  return (
    <section className="py-16 bg-[#f1f1f1]">
      <div className="max-w-7xl mx-auto px-4">
        <h2 className="text-5xl font-bold text-center text-[#7B1E3A] mb-12">
          Our Programs
        </h2>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-10">
          {programs.map((program) => (
            <Card
              key={program.title}
              className="shadow-xl transition-transform transform hover:scale-105 bg-white rounded-lg"
            >
              <CardContent className="p-1.5 text-center space-y-3">
                <div className="flex justify-center mb-4">
                  <img 
                    src={program.image} 
                    alt={`${program.title} icon`}
                    className="w-30 h-30 object-contain"
                  />
                </div>
                <CardTitle className="text-3xl text-center text-[#7B1E3A] font-semibold">
                  {program.title}
                </CardTitle>
                <p className="text-sm text-center font-medium text-gray-700">
                  <span className="font-semibold">Age Group:</span>{" "}
                  {program.age}
                </p>
                <p className="text-gray-600 text-center">{program.description}</p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  );
};

export default CourseComponent;
