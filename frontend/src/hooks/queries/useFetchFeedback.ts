import { useQuery } from "@tanstack/react-query";
import { getDeliveredFeedback, getReceivedFeedback } from "@/apis/feedback.api";
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
  });
};
