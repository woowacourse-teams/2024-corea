import { useQuery } from "@tanstack/react-query";
import { getRevieweeFeedback } from "@/apis/feedback.api";
import QUERY_KEYS from "@/apis/queryKeys";

export const useFetchRevieweeFeedback = (roomId: number, username: string) => {
  return useQuery({
    queryKey: [QUERY_KEYS.REVIEWEE_FEEDBACK, roomId.toString(), username],
    queryFn: () => getRevieweeFeedback(roomId, username),
    enabled: !!roomId && !!username,
  });
};
