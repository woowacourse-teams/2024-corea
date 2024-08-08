import { useQuery } from "@tanstack/react-query";
import { getDeliveredFeedback, getReceivedFeedback, getRevieweeFeedback } from "@/apis/feedback.api";
import QUERY_KEYS from "@/apis/queryKeys";

export const useFetchReceivedFeedback = (enabled: boolean) => {
  return useQuery({
    queryKey: [QUERY_KEYS.RECEIVED_FEEDBACK],
    queryFn: getReceivedFeedback,
    enabled,
  });
};

export const useFetchDeliveredFeedback = (enabled: boolean) => {
  return useQuery({
    queryKey: [QUERY_KEYS.DELIVERED_FEEDBACK],
    queryFn: getDeliveredFeedback,
    enabled,

// 리뷰어 -> 리뷰이
export const useFetchRevieweeFeedback = (roomId: number, username: string) => {
  return useQuery({
    queryKey: [QUERY_KEYS.REVIEWEE_FEEDBACK, roomId.toString(), username],
    queryFn: () => getRevieweeFeedback(roomId, username),
    enabled: !!roomId && !!username,
  });
};
