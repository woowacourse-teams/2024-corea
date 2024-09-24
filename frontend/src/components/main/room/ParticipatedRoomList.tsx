import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import RoomList from "@/components/shared/roomList/RoomList";

const ParticipatedRoomList = () => {
  const { data: participatedRoomList } = useFetchParticipatedRoomList();

  return (
    <ContentSection title="">
      <RoomList roomList={participatedRoomList.rooms} roomType="participated" />
    </ContentSection>
  );
};

export default ParticipatedRoomList;
