import { useSuspenseInfiniteQuery, useSuspenseQuery } from "@tanstack/react-query";
import { RoomListInfo } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import { getParticipantList, getParticipatedRoomList } from "@/apis/rooms.api";

export const useFetchParticipatedRoomList = () => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST],
    queryFn: getParticipatedRoomList,
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
      return lastPage.pageNumber + 1;
    },
    initialPageParam: 0,
  });
};

export const useFetchParticipantList = (roomId: number, isOpenStatus: boolean) => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.PARTICIPANT_LIST, roomId],
    queryFn: async () => {
      if (isOpenStatus) {
        return { participants: [], size: 0 };
      }
      return getParticipantList(roomId);
    },
    staleTime: 5 * 60 * 1000,
    gcTime: Infinity,
  });
};
