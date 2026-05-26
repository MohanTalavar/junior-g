export type Testimonial = {
  id: number;
  parentName: string;
  relation: string;
  rating: 1 | 2 | 3 | 4 | 5;
  message: string;
  imageUrl?: string;
  verified: boolean;
  dateSubmitted: string;
};
