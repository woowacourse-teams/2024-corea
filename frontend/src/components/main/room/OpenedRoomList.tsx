import { useInfiniteFetchRoomList } from "@/hooks/queries/useFetchRooms";
import Loading from "@/components/common/loading/Loading";
import WithSuspense from "@/components/common/withSuspense/WithSuspense";
import RoomListWithDropdown from "@/components/main/room/RoomListWithDropdown";
import QUERY_KEYS from "@/apis/queryKeys";
import { getOpenedRoomList } from "@/apis/rooms.api";

interface OpenedRoomListProps {
  selectedCategory: string;
  handleSelectedCategory: (category: string) => void;
}

const OpenedRoomList = ({ selectedCategory, handleSelectedCategory }: OpenedRoomListProps) => {
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
    <RoomListWithDropdown
      selectedCategory={selectedCategory}
      handleSelectedCategory={handleSelectedCategory}
      roomList={openedRooms}
      hasNextPage={hasNextOpenedPage}
      onLoadMore={fetchNextOpenedPage}
      isFetchingNextPage={isFetchingNextPage}
      roomType="opened"
    />
  );
};

export default WithSuspense(OpenedRoomList, <Loading />);
