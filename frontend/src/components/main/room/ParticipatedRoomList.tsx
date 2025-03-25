import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import Loading from "@/components/common/loading/Loading";
import WithSuspense from "@/components/common/withSuspense/WithSuspense";
import RoomList from "@/components/shared/roomList/RoomList";

const ParticipatedRoomList = () => {
  const { data: participatedRoomList } = useFetchParticipatedRoomList(false);

  return (
    <RoomList
      isFetchingNextPage={false}
      roomList={participatedRoomList.rooms}
      roomType="participated"
    />
  );
};

export default WithSuspense(ParticipatedRoomList, <Loading />);
