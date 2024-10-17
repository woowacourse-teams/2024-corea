import { useParams } from "react-router-dom";
import { useFetchDetailRoomInfo } from "@/hooks/queries/useFetchRooms";
import RoomFormLayout from "@/components/roomForm/RoomFormLayout";

const RoomEditPage = () => {
  const roomId = Number(useParams().roomId);
  const { data: roomInfo } = useFetchDetailRoomInfo(roomId);

  return <RoomFormLayout formType="edit" roomId={roomId} data={roomInfo} />;
};

export default RoomEditPage;
