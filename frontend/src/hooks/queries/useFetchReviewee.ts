import { useQuery } from "@tanstack/react-query";
import { RoomInfo } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import { getMyReviewees } from "@/apis/reviews.api";

export const useFetchReviewee = (roomInfo: RoomInfo) => {
  return useQuery({
    queryKey: [QUERY_KEYS.REVIEWEES, roomInfo.id],
    queryFn: () => getMyReviewees(roomInfo.id),
  });
};