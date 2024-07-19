import { Link } from "react-router-dom";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";

const RoomList = ({ roomList }: { roomList: RoomInfo[] }) => {
  if (!roomList) return <></>;

  return (
    <S.RoomListContainer>
      {roomList.map((roomInfo) => (
        <Link to={`/room/1`}>
          <RoomCard key={roomInfo.id} roomInfo={roomInfo} />
        </Link>
      ))}
    </S.RoomListContainer>
  );
};

export default RoomList;
