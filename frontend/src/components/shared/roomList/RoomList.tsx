import React from "react";
import { Link } from "react-router-dom";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";
import { API_ENDPOINTS } from "@/apis/endpoints";

const RoomList = ({ roomList }: { roomList: RoomInfo[] }) => {
  if (!roomList) return <></>;

  return (
    <S.RoomListContainer>
      {roomList.map((roomInfo) => (
        <Link to={`${API_ENDPOINTS.ROOMS}/${roomInfo.id}`} key={roomInfo.id}>
          <RoomCard roomInfo={roomInfo} />
        </Link>
      ))}
    </S.RoomListContainer>
  );
};

export default RoomList;
