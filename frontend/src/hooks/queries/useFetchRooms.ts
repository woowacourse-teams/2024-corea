import { useQuery, useSuspenseInfiniteQuery, useSuspenseQuery } from "@tanstack/react-query";
import { Classification, RoomListInfo, RoomStatus, RoomStatusCategory } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import {
  getParticipantList,
  getParticipatedRoomList,
  getRoomDetailInfo,
  getSearchRoomList,
} from "@/apis/rooms.api";

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

export const useFetchSearchRoomList = (
  status: RoomStatusCategory,
  classification: Classification,
  keyword: string,
  enabled: boolean,
) => {
  let roomStatus: RoomStatus;

  switch (status) {
    case "opened": {
      roomStatus = "OPEN";
      break;
    }
    case "closed": {
      roomStatus = "CLOSE";
      break;
    }
    case "progress": {
      roomStatus = "PROGRESS";
      break;
    }
    default: {
      roomStatus = "OPEN";
    }
  }

  return useQuery({
    queryKey: [QUERY_KEYS.SEARCH_ROOM_LIST, status, classification, keyword],
    queryFn: () => getSearchRoomList(roomStatus, classification, keyword),
    enabled,
  });
};

export const useFetchDetailRoomInfo = (roomId: number) => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.ROOM_DETAIL_INFO, roomId],
    queryFn: () => getRoomDetailInfo(roomId),
  });
};

export const useFetchParticipatedRoomList = (includeClosed: boolean = false) => {
  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST, includeClosed],
    queryFn: () => getParticipatedRoomList(includeClosed),
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
  });
};
