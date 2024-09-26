import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import RoomList from "@/components/shared/roomList/RoomList";

const ParticipatedRoomList = () => {
  const { data: participatedRoomList, isFetching } = useFetchParticipatedRoomList();

  return (
    <ContentSection title="">
      <RoomList
        isFetching={isFetching}
        roomList={participatedRoomList.rooms}
        roomType="participated"
      />
    </ContentSection>
  );
};

export default ParticipatedRoomList;
