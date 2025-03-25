import { useInfiniteFetchRoomList } from "@/hooks/queries/useFetchRooms";
import RoomList from "@/components/shared/roomList/RoomList";
import QUERY_KEYS from "@/apis/queryKeys";
import { getOpenedRoomList } from "@/apis/rooms.api";

interface OpenedRoomListProps {
  selectedCategory: string;
}

const OpenedRoomList = ({ selectedCategory }: OpenedRoomListProps) => {
  const {
    data: openedRoomList,
    fetchNextPage: fetchNextOpenedPage,
    hasNextPage: hasNextOpenedPage,
    isFetchingNextPage,
  } = useInfiniteFetchRoomList({
    queryKey: [QUERY_KEYS.OPENED_ROOM_LIST, selectedCategory],
    getRoomList: getOpenedRoomList,
    classification: selectedCategory,
  });

  const openedRooms = openedRoomList.pages.flatMap((page) => page.rooms) || [];

  return (
    <RoomList
      roomList={openedRooms}
      hasNextPage={hasNextOpenedPage}
      onLoadMore={fetchNextOpenedPage}
      isFetchingNextPage={isFetchingNextPage}
      roomType="opened"
    />
  );
};

export default OpenedRoomList;
