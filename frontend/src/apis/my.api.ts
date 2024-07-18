import { ReviewerInfo } from "./../@types/reviewer";
import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";

export const getMyReviewers = async (): Promise<ReviewerInfo[]> => {
  const res = await apiClient<any>({
    method: "get",
    url: API_ENDPOINTS.REVIEWER,
  });

  return res.data;
};

export const getMyReviewees = async (): Promise<ReviewerInfo[]> => {
  const res = await apiClient<any>({
    method: "get",
    url: API_ENDPOINTS.REVIEWEE,
  });

  return res.data;
};
