import React from "react";
import { Link } from "react-router-dom";
import PlusButton from "@/components/common/plusButton/PlusButton";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";

interface RoomListProps {
  roomList: RoomInfo[];
  hasNextPage?: boolean;
  onLoadMore?: () => void;
}

const RoomList = ({ roomList, hasNextPage, onLoadMore }: RoomListProps) => {
  if (!roomList || roomList.length === 0) return <></>;

  return (
    <S.RoomListSection>
      <S.RoomListContainer>
        {roomList &&
          roomList.map((roomInfo) =>
            hasNextPage ? (
              <RoomCard roomInfo={roomInfo} key={roomInfo.id} />
            ) : (
              <Link to={`/rooms/${roomInfo.id}`} key={roomInfo.id}>
                <RoomCard roomInfo={roomInfo} />
              </Link>
            ),
          )}
      </S.RoomListContainer>
      {hasNextPage && onLoadMore && <PlusButton onClick={onLoadMore} />}
    </S.RoomListSection>
  );
};

export default RoomList;
