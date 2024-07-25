import { useInfiniteQuery } from "@tanstack/react-query";
import { RoomListInfo } from "@/@types/roomInfo";

interface RoomListQueryProps {
  queryKey: string[];
  getRoomList: (status: string, page: number) => Promise<RoomListInfo>;
  classification: string;
}

export const useGetRoomList = ({ queryKey, getRoomList, classification }: RoomListQueryProps) => {
  return useInfiniteQuery({
    queryKey,
    queryFn: ({ pageParam = 1 }) => getRoomList(classification, pageParam),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.isLastPage ? undefined : allPages.length + 1,
    initialPageParam: 1,
  });
};
