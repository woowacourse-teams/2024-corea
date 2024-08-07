import { RevieweeFeedbackData } from "@/@types/feedback";
import apiClient from "@/apis/apiClient";
import { API_ENDPOINTS } from "@/apis/endpoints";
import MESSAGES from "@/constants/message";

export const getRevieweeFeedback = async (
  roomId: number,
  username: string,
): Promise<RevieweeFeedbackData> => {
  const res = await apiClient.get({
    endpoint: `${API_ENDPOINTS.REVIEWEE_FEEDBACK(roomId)}?username=${username}`,
    errorMessage: MESSAGES.ERROR.GET_REVIEWEE_FEEDBACK,
  });

  return res;
};

export const postRevieweeFeedback = async (
  roomId: number,
  feedbackData: Omit<RevieweeFeedbackData, "feedbackId">,
) => {
  const res = await apiClient.post({
    endpoint: API_ENDPOINTS.REVIEWEE_FEEDBACK(roomId),
    body: feedbackData,
    errorMessage: MESSAGES.ERROR.POST_REVIEWEE_FEEDBACK,
  });

  return res;
};
