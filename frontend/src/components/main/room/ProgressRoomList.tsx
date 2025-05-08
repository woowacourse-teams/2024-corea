import { useInfiniteFetchRoomList } from "@/hooks/queries/useFetchRooms";
import RoomList from "@/components/shared/roomList/RoomList";
import QUERY_KEYS from "@/apis/queryKeys";
import { getProgressRoomList } from "@/apis/rooms.api";

interface ProgressRoomListProps {
  selectedCategory: string;
}

const ProgressRoomList = ({ selectedCategory }: ProgressRoomListProps) => {
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
    <RoomList
      roomList={progressRooms}
      hasNextPage={hasNextProgressPage}
      onLoadMore={fetchNextProgressPage}
      isFetchingNextPage={isFetchingNextPage}
      roomType="progress"
    />
  );
};

export default ProgressRoomList;
