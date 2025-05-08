import { useInfiniteFetchRoomList } from "@/hooks/queries/useFetchRooms";
import RoomList from "@/components/shared/roomList/RoomList";
import QUERY_KEYS from "@/apis/queryKeys";
import { getClosedRoomList } from "@/apis/rooms.api";

interface ClosedRoomListProps {
  selectedCategory: string;
}

const ClosedRoomList = ({ selectedCategory }: ClosedRoomListProps) => {
  const {
    data: closedRoomList,
    fetchNextPage: fetchNextClosedPage,
    hasNextPage: hasNextClosedPage,
    isFetchingNextPage,
  } = useInfiniteFetchRoomList({
    queryKey: [QUERY_KEYS.CLOSED_ROOM_LIST, selectedCategory],
    getRoomList: getClosedRoomList,
    classification: selectedCategory,
  });

  const closedRooms = closedRoomList?.pages.flatMap((page) => page.rooms) || [];

  return (
    <RoomList
      roomList={closedRooms}
      hasNextPage={hasNextClosedPage}
      onLoadMore={fetchNextClosedPage}
      isFetchingNextPage={isFetchingNextPage}
      roomType="closed"
    />
  );
};

export default ClosedRoomList;
