import summerCampImg from "../assets/images/juniorg-summercamp.jpg";
const SummerCampPage = () => {
  return (
    <div className="min-h-screen bg-white flex flex-col items-center justify-center p-8">
      <h1 className="text-3xl font-bold text-[#990000] mb-6 font-serif">
        Junior G Summer Camp 2025
      </h1>

      {/* Display a pamphlet image */}
      <img
        src={summerCampImg}
        alt="Junior G Summer Camp"
        className="w-full max-w-2xl rounded-lg shadow-md"
      />

      {/* OR display a PDF embed instead */}
      {/* <iframe
          src="/pamphlets/juniorg-summercamp.pdf"
          className="w-full h-[90vh] mt-4"
          title="Summer Camp Brochure"
        /> */}
    </div>
  );
};

export default SummerCampPage;
