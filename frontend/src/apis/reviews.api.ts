import { ReviewInfo } from "../@types/review";
import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { ReviewReminderAlarm } from "@/@types/alarm";
import MESSAGES from "@/constants/message";

export const getMyReviewers = async (roomId: number): Promise<ReviewInfo[]> => {
  const { data } = await apiClient.get({
    endpoint: API_ENDPOINTS.REVIEWERS(roomId),
    errorMessage: MESSAGES.ERROR.GET_MY_REVIEWERS,
  });

  return data.matchResultResponses;
};

export const getMyReviewees = async (roomId: number): Promise<ReviewInfo[]> => {
  const { data } = await apiClient.get({
    endpoint: API_ENDPOINTS.REVIEWEES(roomId),
    errorMessage: MESSAGES.ERROR.GET_MY_REVIEWEES,
  });

  return data.matchResultResponses;
};

export const postReviewComplete = async (roomId: number, revieweeId: number): Promise<void> => {
  await apiClient.post({
    endpoint: API_ENDPOINTS.REVIEW_COMPLETE,
    body: {
      roomId,
      revieweeId,
    },
    errorMessage: MESSAGES.ERROR.POST_REVIEW_COMPLETE,
  });
};

export const postReviewUrge = async ({
  roomId,
  reviewerId,
}: ReviewReminderAlarm): Promise<void> => {
  return apiClient.post({
    endpoint: API_ENDPOINTS.REVIEW_URGE,
    body: { roomId, reviewerId },
    errorMessage: MESSAGES.ERROR.POST_REVIEW_URGE,
  });
};
