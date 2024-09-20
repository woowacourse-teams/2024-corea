import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import RoomList from "@/components/shared/roomList/RoomList";

const UserParticipatedRoom = () => {
  const { data: roomList, isFetching } = useFetchParticipatedRoomList();

  const participatingRoomList = roomList.rooms.filter((room) => !room.isClosed);
  const participatedRoomList = roomList.rooms.filter((room) => room.isClosed);

  return (
    <>
      <ContentSection title="참여 중인 방 리스트">
        <RoomList isFetching={isFetching} roomList={participatingRoomList} participated={true} />
      </ContentSection>
      <ContentSection title="참여 했던 방 리스트">
        <RoomList isFetching={isFetching} roomList={participatedRoomList} participated={true} />
      </ContentSection>
    </>
  );
};

export default UserParticipatedRoom;
