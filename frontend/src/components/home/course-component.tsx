import { Card, CardContent, CardTitle } from "@/components/ui/card";

const programs = [
  {
    title: "Daycare",
    age: "2 – 10 years",
    description:
      "Safe, flexible care for working parents. Engaging environment with meals, naps, and play.",
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
  },
  {
    title: "Nursery",
    age: "3 – 4 years",
    description:
      "Hands-on learning to develop pre-writing, pre-math, and problem-solving skills.",
  },
  {
    title: "LKG",
    age: "4 – 5 years",
    description:
      "Foundational education focusing on language, numbers, art, and social interaction.",
  },
  {
    title: "UKG",
    age: "5 – 6 years",
    description:
      "Advanced prep for Grade 1 with reading, writing, math, and concept-based learning.",
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
              <CardContent className="p-6 text-left space-y-3">
                <CardTitle className="text-3xl text-center text-[#7B1E3A] font-semibold">
                  {program.title}
                </CardTitle>
                <p className="text-sm text-center font-medium text-gray-700">
                  <span className="font-semibold">Age Group:</span>{" "}
                  {program.age}
                </p>
                <p className="text-gray-600">{program.description}</p>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>
    </section>
  );
};

export default CourseComponent;
