import { useQuery } from "@tanstack/react-query";
import { getRevieweeFeedback, getReviewerFeedback } from "@/apis/feedback.api";
import QUERY_KEYS from "@/apis/queryKeys";

// 리뷰어 -> 리뷰이
export const useFetchRevieweeFeedback = (roomId: number, username: string) => {
  return useQuery({
    queryKey: [QUERY_KEYS.REVIEWEE_FEEDBACK, roomId.toString(), username],
    queryFn: () => getRevieweeFeedback(roomId, username),
    enabled: !!roomId && !!username,
  });
};

// 리뷰이 -> 리뷰어
export const useFetchReviewerFeedback = (roomId: number, username: string) => {
  return useQuery({
    queryKey: [QUERY_KEYS.REVIEWER_FEEDBACK, roomId.toString(), username],
    queryFn: () => getReviewerFeedback(roomId, username),
    enabled: !!roomId && !!username,
  });
};
