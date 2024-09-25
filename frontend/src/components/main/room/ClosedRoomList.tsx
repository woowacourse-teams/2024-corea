import { useInfiniteFetchRoomList } from "@/hooks/queries/useFetchRooms";
import RoomListWithDropdown from "@/components/main/room/RoomListWithDropdown";
import QUERY_KEYS from "@/apis/queryKeys";
import { getClosedRoomList } from "@/apis/rooms.api";

interface ClosedRoomListProps {
  selectedCategory: string;
  handleSelectedCategory: (category: string) => void;
}

const ClosedRoomList = ({ selectedCategory, handleSelectedCategory }: ClosedRoomListProps) => {
  const {
    data: closedRoomList,
    fetchNextPage: fetchNextClosedPage,
    hasNextPage: hasNextClosedPage,
    isFetching,
  } = useInfiniteFetchRoomList({
    queryKey: [QUERY_KEYS.CLOSED_ROOM_LIST, selectedCategory],
    getRoomList: getClosedRoomList,
    classification: selectedCategory,
  });

  const closedRooms = closedRoomList?.pages.flatMap((page) => page.rooms) || [];

  return (
    <RoomListWithDropdown
      selectedCategory={selectedCategory}
      handleSelectedCategory={handleSelectedCategory}
      roomList={closedRooms}
      hasNextPage={hasNextClosedPage}
      onLoadMore={fetchNextClosedPage}
      isFetching={isFetching}
      roomType="closed"
    />
  );
};

export default ClosedRoomList;
