import { useQuery } from "@tanstack/react-query";
import { RoomInfo } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import { getMyReviewers } from "@/apis/reviews.api";

export const useFetchReviewer = (roomInfo: RoomInfo) => {
  return useQuery({
    queryKey: [QUERY_KEYS.REVIEWERS, roomInfo.id],
    queryFn: () => getMyReviewers(roomInfo.id),
  });
};
