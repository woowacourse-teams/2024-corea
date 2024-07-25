import { ReviewerInfo } from "../@types/reviewer";
import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";

export const getMyReviewers = async (roomId: number): Promise<ReviewerInfo[]> => {
  const res = await apiClient<{ reviewInfo: ReviewerInfo[] }>({
    method: "get",
    url: API_ENDPOINTS.REVIEWERS(roomId),
  });

  return res.data.reviewInfo;
};

export const getMyReviewees = async (roomId: number): Promise<ReviewerInfo[]> => {
  const res = await apiClient<{ reviewInfo: ReviewerInfo[] }>({
    method: "get",
    url: API_ENDPOINTS.REVIEWEES(roomId),
  });

  return res.data.reviewInfo;
};
