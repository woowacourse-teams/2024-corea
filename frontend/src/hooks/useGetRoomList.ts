import { useInfiniteQuery } from "@tanstack/react-query";
import { useSearchParams } from "react-router-dom";
import { RoomListInfo } from "@/@types/roomInfo";

interface RoomListQueryProps {
  queryKey: string[];
  getRoomList: (status: string, page: number) => Promise<RoomListInfo>;
  classification: string;
}

export const useGetRoomList = ({ queryKey, getRoomList, classification }: RoomListQueryProps) => {
  const [searchParams] = useSearchParams();

  return useInfiniteQuery({
    queryKey,
    queryFn: ({ pageParam = 0 }) => {
      return getRoomList(classification, pageParam);
    },
    getNextPageParam: (lastPage) => {
      if (lastPage.isLastPage) return undefined;
      return Number(searchParams.get("page")) + 1;
    },
    initialPageParam: 0,
    networkMode: "always",
    retry: false,
  });
};
