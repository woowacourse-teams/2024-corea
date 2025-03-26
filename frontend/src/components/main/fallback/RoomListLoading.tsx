import React from "react";
import * as S from "@/components/shared/roomList/RoomList.style";
import RoomListSkeleton from "@/components/shared/roomList/RoomListSkeleton";

const RoomListLoading = () => {
  return (
    <S.RoomListSection>
      <S.RoomListContainer>
        <RoomListSkeleton />
      </S.RoomListContainer>
    </S.RoomListSection>
  );
};

export default RoomListLoading;
