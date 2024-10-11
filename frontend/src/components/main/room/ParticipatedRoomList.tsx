import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Loading from "@/components/common/loading/Loading";
import WithSuspense from "@/components/common/withSuspense/WithSuspense";
import RoomList from "@/components/shared/roomList/RoomList";

const ParticipatedRoomList = () => {
  const { data: participatedRoomList } = useFetchParticipatedRoomList();

  return (
    <ContentSection title="">
      <RoomList
        isFetchingNextPage={false}
        roomList={participatedRoomList.rooms}
        roomType="participated"
      />
    </ContentSection>
  );
};

export default WithSuspense(ParticipatedRoomList, <Loading />);
