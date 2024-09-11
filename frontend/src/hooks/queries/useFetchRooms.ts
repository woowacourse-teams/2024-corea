import { useSuspenseInfiniteQuery, useSuspenseQuery } from "@tanstack/react-query";
import { RoomListInfo } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import { getParticipatedRoomList } from "@/apis/rooms.api";

export const useFetchParticipatedRoomList = () => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST],
    queryFn: getParticipatedRoomList,
    networkMode: "always",
    retry: false,
  });
};

interface RoomListQueryProps {
  queryKey: string[];
  getRoomList: (status: string, page: number) => Promise<RoomListInfo>;
  classification: string;
}

export const useInfiniteFetchRoomList = ({
  queryKey,
  getRoomList,
  classification,
}: RoomListQueryProps) => {
  return useSuspenseInfiniteQuery({
    queryKey,
    queryFn: ({ pageParam = 0 }) => {
      return getRoomList(classification, pageParam);
    },
    getNextPageParam: (lastPage) => {
      if (lastPage.isLastPage) return undefined;
      return lastPage.pageNumber;
    },
    initialPageParam: 0,
    networkMode: "always",
    retry: false,
  });
};
