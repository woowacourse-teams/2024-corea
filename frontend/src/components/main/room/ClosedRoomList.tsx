import { useInfiniteFetchRoomList } from "@/hooks/queries/useFetchRooms";
import Loading from "@/components/common/loading/Loading";
import WithSuspense from "@/components/common/withSuspense/WithSuspense";
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
    isFetchingNextPage,
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
      isFetchingNextPage={isFetchingNextPage}
      roomType="closed"
    />
  );
};

export default WithSuspense(ClosedRoomList, <Loading />);
