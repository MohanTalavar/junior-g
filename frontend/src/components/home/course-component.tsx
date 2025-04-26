import { Card, CardContent, CardTitle } from "@/components/ui/card";
const CourseComponent: React.FC = () => {
  return (
    <div>
      {/* Courses Section */}
      <section className="py-12 bg-gradient-to-r from-orange-200 to-purple-100">
        <div className="max-w-7xl mx-auto px-4">
          <h2 className="text-3xl font-bold text-center text-orange-600 mb-10">
            Our Programs
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-8">
            {[
              "Daycare",
              "PreSchool",
              "Play Group",
              "Nursery",
              "LKG",
              "UKG",
            ].map((course) => (
              <Card
                key={course}
                className="shadow-lg hover:scale-105 transition-transform bg-white"
              >
                <CardContent className="p-6 text-center">
                  <CardTitle className="text-xl text-purple-700 mb-2">
                    {course}
                  </CardTitle>
                  <p className="text-gray-600">
                    Fun, nurturing programs for a strong start to lifelong
                    learning!
                  </p>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>
    </div>
  );
};

export default CourseComponent;
