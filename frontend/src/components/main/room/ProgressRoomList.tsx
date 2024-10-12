import { useInfiniteFetchRoomList } from "@/hooks/queries/useFetchRooms";
import Loading from "@/components/common/loading/Loading";
import WithSuspense from "@/components/common/withSuspense/WithSuspense";
import RoomListWithDropdown from "@/components/main/room/RoomListWithDropdown";
import QUERY_KEYS from "@/apis/queryKeys";
import { getProgressRoomList } from "@/apis/rooms.api";

interface ProgressRoomListProps {
  selectedCategory: string;
  handleSelectedCategory: (category: string) => void;
}

const ProgressRoomList = ({ selectedCategory, handleSelectedCategory }: ProgressRoomListProps) => {
  const {
    data: progressRoomList,
    fetchNextPage: fetchNextProgressPage,
    hasNextPage: hasNextProgressPage,
    isFetchingNextPage,
  } = useInfiniteFetchRoomList({
    queryKey: [QUERY_KEYS.PROGRESS_ROOM_LIST, selectedCategory],
    getRoomList: getProgressRoomList,
    classification: selectedCategory,
  });

  const progressRooms = progressRoomList?.pages.flatMap((page) => page.rooms) || [];

  return (
    <RoomListWithDropdown
      isFetchingNextPage={isFetchingNextPage}
      selectedCategory={selectedCategory}
      handleSelectedCategory={handleSelectedCategory}
      roomList={progressRooms}
      hasNextPage={hasNextProgressPage}
      onLoadMore={fetchNextProgressPage}
      roomType="progress"
    />
  );
};

export default WithSuspense(ProgressRoomList, <Loading />);
