import { useFetchParticipatedRoomList } from "@/hooks/queries/useFetchRooms";
import RoomList from "@/components/shared/roomList/RoomList";

const ParticipatedRoomList = () => {
  const { data: participatedRoomList } = useFetchParticipatedRoomList(false);

  return <RoomList roomList={participatedRoomList.rooms} roomType="participated" />;
};

export default ParticipatedRoomList;
