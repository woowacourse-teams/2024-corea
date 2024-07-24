import { Link, Navigate } from "react-router-dom";
import useModal from "@/hooks/useModal";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";

const RoomList = ({ roomList }: { roomList: RoomInfo[] }) => {
  if (!roomList) return <></>;

  return (
    <S.RoomListContainer>
      {roomList.map((roomInfo) => (
        <Link to={`/room/1`} key={roomInfo.id}>
          <RoomCard key={roomInfo.id} roomInfo={roomInfo} />
        </Link>
      ))}
    </S.RoomListContainer>
  );
};

export default RoomList;
