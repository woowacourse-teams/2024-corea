import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import RoomList from "@/components/shared/roomList/RoomList";

const UserParticipatedRoom = () => {
  const { data: roomList } = useFetchParticipatedRoomList();

  const participatingRoomList = roomList.rooms.filter((room) => room.roomStatus !== "CLOSE");
  const participatedRoomList = roomList.rooms.filter((room) => room.roomStatus === "CLOSE");

  return (
    <>
      <ContentSection title="참여 중인 방 리스트">
        <RoomList
          isFetchingNextPage={false}
          roomList={participatingRoomList}
          roomType="participated"
        />
      </ContentSection>
      <ContentSection title="참여 했던 방 리스트">
        <RoomList
          isFetchingNextPage={false}
          roomList={participatedRoomList}
          roomType="participated"
        />
      </ContentSection>
    </>
  );
};

export default UserParticipatedRoom;
