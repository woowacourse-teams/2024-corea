import { FeedbackCardList } from "@/@types/feedback";
import apiClient from "@/apis/apiClient";
import { API_ENDPOINTS } from "@/apis/endpoints";
import MESSAGES from "@/constants/message";

export const getReceivedFeedback = async (): Promise<FeedbackCardList[]> => {
  const res = await apiClient.get({
    endpoint: API_ENDPOINTS.RECEIVED_FEEDBACK,
    errorMessage: MESSAGES.ERROR.GET_RECEIVED_FEEDBACK,
  });

  return res.feedbacks;
};

export const getDeliveredFeedback = async (): Promise<FeedbackCardList[]> => {
  const res = await apiClient.get({
    endpoint: API_ENDPOINTS.DELIVERED_FEEDBACK,
    errorMessage: MESSAGES.ERROR.GET_DELIVERED_FEEDBACK,
  });

  return res.feedbacks;
};
