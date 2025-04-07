import { useQuery, useSuspenseQuery } from "@tanstack/react-query";
import {
  getDeliveredFeedback,
  getReceivedFeedback,
  getRevieweeFeedback,
  getReviewerFeedback,
} from "@/apis/feedback.api";
import QUERY_KEYS from "@/apis/queryKeys";

interface UseFetchFeedbackProps {
  roomId: number;
  username: string;
  enabled: boolean;
}

export const useFetchReceivedFeedback = () => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.RECEIVED_FEEDBACK],
    queryFn: getReceivedFeedback,
  });
};

export const useFetchDeliveredFeedback = () => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.DELIVERED_FEEDBACK],
    queryFn: getDeliveredFeedback,
  });
};

// 리뷰어 -> 리뷰이
export const useFetchRevieweeFeedback = ({ roomId, username, enabled }: UseFetchFeedbackProps) => {
  return useQuery({
    queryKey: [QUERY_KEYS.REVIEWEE_FEEDBACK, roomId.toString(), username],
    queryFn: () => getRevieweeFeedback(roomId, username),
    enabled: enabled && !!roomId && !!username,
  });
};

// 리뷰이 -> 리뷰어
export const useFetchReviewerFeedback = ({ roomId, username, enabled }: UseFetchFeedbackProps) => {
  return useQuery({
    queryKey: [QUERY_KEYS.REVIEWER_FEEDBACK, roomId.toString(), username],
    queryFn: () => getReviewerFeedback(roomId, username),
    enabled: enabled && !!roomId && !!username,
  });
};
