import React, { useCallback, useEffect, useState } from "react";
import useEmblaCarousel from "embla-carousel-react";
import { ChevronLeft, ChevronRight, Star, CheckCircle2 } from "lucide-react";
import { Testimonial } from "../../types/testimonial";
import placeholderImg from "../../assets/images/placeholder-parent.svg";
import { cn } from "../../lib/utils";

interface TestimonialComponentProps {
  testimonials?: Testimonial[];
  autoPlayInterval?: number;
}

const TestimonialComponent: React.FC<TestimonialComponentProps> = ({
  testimonials = [],
  autoPlayInterval = 5000,
}) => {
  const [emblaRef, emblaApi] = useEmblaCarousel({
    loop: true,
    align: "center",
  });

  const [prevBtnEnabled, setPrevBtnEnabled] = useState(false);
  const [nextBtnEnabled, setNextBtnEnabled] = useState(false);
  const [current, setCurrent] = useState(0);
  const [isHovered, setIsHovered] = useState(false);

  const onSelect = useCallback(() => {
    if (!emblaApi) return;
    setCurrent(emblaApi.selectedScrollSnap());
  }, [emblaApi]);

  const onPrevButtonClick = useCallback(() => {
    if (!emblaApi) return;
    emblaApi.scrollPrev();
  }, [emblaApi]);

  const onNextButtonClick = useCallback(() => {
    if (!emblaApi) return;
    emblaApi.scrollNext();
  }, [emblaApi]);

  const onDotClick = useCallback(
    (index: number) => {
      if (!emblaApi) return;
      emblaApi.scrollTo(index);
    },
    [emblaApi],
  );

  useEffect(() => {
    if (!emblaApi) return;

    onSelect();
    emblaApi.on("select", onSelect);

    const updateButtons = () => {
      setPrevBtnEnabled(emblaApi.canScrollPrev());
      setNextBtnEnabled(emblaApi.canScrollNext());
    };

    updateButtons();
    emblaApi.on("reInit", updateButtons);
    emblaApi.on("select", updateButtons);

    return () => {
      emblaApi.off("select", onSelect);
      emblaApi.off("reInit", updateButtons);
    };
  }, [emblaApi, onSelect]);

  // Auto-play effect
  useEffect(() => {
    if (!emblaApi || isHovered) return;

    const autoplay = setInterval(() => {
      emblaApi.scrollNext();
    }, autoPlayInterval);

    return () => clearInterval(autoplay);
  }, [emblaApi, isHovered, autoPlayInterval]);

  const renderStars = (rating: number) => {
    return (
      <div className="flex gap-1">
        {Array.from({ length: 5 }).map((_, i) => (
          <Star
            key={i}
            size={18}
            className={cn(
              "transition-all duration-300",
              i < rating ? "fill-yellow-400 text-yellow-400" : "text-gray-300",
            )}
          />
        ))}
      </div>
    );
  };

  if (!testimonials || testimonials.length === 0) {
    return null;
  }

  return (
    <section className="w-full py-16 bg-white">
      <div className="max-w-7xl mx-auto px-4">
        {/* Section Header */}
        <div className="mb-12 text-center">
          <h2 className="text-4xl font-bold text-gray-900 mb-3">
            What Parents Say
          </h2>
          <p className="text-lg text-gray-600 max-w-2xl mx-auto">
            Hear from families who have trusted us with their little ones
          </p>
        </div>

        {/* Carousel Container */}
        <div
          className="relative"
          onMouseEnter={() => setIsHovered(true)}
          onMouseLeave={() => setIsHovered(false)}
        >
          {/* Embla Carousel */}
          <div className="overflow-hidden" ref={emblaRef}>
            <div className="flex">
              {testimonials.map((testimonial) => (
                <div
                  key={testimonial.id}
                  className="flex-[0_0_100%] min-w-0 md:flex-[0_0_85%] md:pl-8"
                >
                  {/* Testimonial Card */}
                  <div className="bg-gradient-to-br from-blue-50 to-purple-50 rounded-2xl shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-105 p-8 md:p-10 h-full">
                    {/* Quote Mark */}
                    <div className="text-6xl text-purple-200 opacity-40 mb-2 leading-none">
                      "
                    </div>

                    {/* Message */}
                    <p className="text-base md:text-lg text-gray-700 leading-relaxed mb-6">
                      {testimonial.message}
                    </p>

                    {/* Star Rating */}
                    <div className="mb-6">
                      {renderStars(testimonial.rating)}
                    </div>

                    {/* Parent Info Section */}
                    <div className="flex items-center gap-4 pt-4 border-t border-gray-200">
                      {/* Parent Image */}
                      <div className="flex-shrink-0">
                        <img
                          src={testimonial.imageUrl || placeholderImg}
                          alt={testimonial.parentName}
                          className="w-16 h-16 rounded-full object-cover shadow-md"
                        />
                      </div>

                      {/* Parent Info */}
                      <div className="flex-1">
                        <div className="flex items-center gap-2">
                          <h3 className="font-bold text-lg text-gray-900">
                            {testimonial.parentName}
                          </h3>
                          {testimonial.verified && (
                            <CheckCircle2
                              size={18}
                              className="text-green-500 flex-shrink-0"
                            />
                          )}
                        </div>
                        <p className="text-sm text-gray-600">
                          {testimonial.relation}
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Navigation Buttons */}
          <button
            onClick={onPrevButtonClick}
            disabled={!prevBtnEnabled}
            className="absolute left-0 top-1/2 -translate-y-1/2 -translate-x-16 md:-translate-x-8 z-10 p-2 rounded-full bg-gray-200 hover:bg-purple-500 text-gray-700 hover:text-white transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
            aria-label="Previous testimonial"
          >
            <ChevronLeft size={24} />
          </button>

          <button
            onClick={onNextButtonClick}
            disabled={!nextBtnEnabled}
            className="absolute right-0 top-1/2 -translate-y-1/2 translate-x-16 md:translate-x-8 z-10 p-2 rounded-full bg-gray-200 hover:bg-purple-500 text-gray-700 hover:text-white transition-all duration-200 disabled:opacity-50 disabled:cursor-not-allowed"
            aria-label="Next testimonial"
          >
            <ChevronRight size={24} />
          </button>
        </div>

        {/* Navigation Indicators */}
        <div className="flex items-center justify-center gap-4 mt-8">
          {/* Dot Indicators */}
          <div className="flex gap-2">
            {testimonials.map((_, index) => (
              <button
                key={index}
                onClick={() => onDotClick(index)}
                className={cn(
                  "w-2 h-2 rounded-full transition-all duration-300 cursor-pointer",
                  index === current
                    ? "bg-purple-500 w-8"
                    : "bg-gray-300 hover:bg-gray-400",
                )}
                aria-label={`Go to testimonial ${index + 1}`}
              />
            ))}
          </div>

          {/* Slide Counter */}
          <span className="text-sm font-medium text-gray-600 ml-4">
            {current + 1} / {testimonials.length}
          </span>
        </div>
      </div>
    </section>
  );
};

export default TestimonialComponent;
