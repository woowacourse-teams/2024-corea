import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import RoomList from "@/components/shared/roomList/RoomList";

const ParticipatedRoomList = () => {
  const { data: participatedRoomList } = useFetchParticipatedRoomList();

  return (
    <ContentSection title="">
      {participatedRoomList ? (
        <RoomList roomList={participatedRoomList.rooms} roomType="participated" />
      ) : (
        <div>❗ 참여 중인 방이 없습니다.</div>
      )}
    </ContentSection>
  );
};

export default ParticipatedRoomList;
