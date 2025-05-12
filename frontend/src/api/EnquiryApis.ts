import api from "./Junior_G_Apis";

export type Enquiry = {
  studentName: string;
  parentName: string;
  emailId: string;
  contactNo: string;
  courseName: string;
};

export const submitEnquiry = async (enquiryData: Enquiry): Promise<string> => {
  const response = await api.post<string>("/enquiry", enquiryData);
  return response.data;
};
