import { Link } from "react-router-dom";
import PlusButton from "@/components/common/plusButton/PlusButton";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";
import { defaultCharacter } from "@/assets";

interface RoomListProps {
  roomList: RoomInfo[];
  hasNextPage?: boolean;
  onLoadMore?: () => void;
  roomType: "participated" | "progress" | "opened" | "closed";
}

const RoomEmptyText = {
  participated: "참여한 방이 없습니다.",
  progress: "진행 중인 방이 없습니다.",
  opened: "모집 중인 방이 없습니다.",
  closed: "모집 마감된 방이 없습니다.",
};

const RoomList = ({ roomList, hasNextPage, onLoadMore, roomType }: RoomListProps) => {
  if (roomList.length === 0) {
    return (
      <S.EmptyContainer>
        <S.Character src={defaultCharacter} alt="기본 캐릭터" />
        <p>{RoomEmptyText[roomType]}</p>
      </S.EmptyContainer>
    );
  }

  return (
    <S.RoomListSection>
      <S.RoomListContainer>
        {roomList.map((roomInfo) =>
          roomType === "participated" ? (
            <Link to={`/rooms/${roomInfo.id}`} key={roomInfo.id}>
              <RoomCard roomInfo={roomInfo} />
            </Link>
          ) : (
            <RoomCard roomInfo={roomInfo} key={roomInfo.id} />
          ),
        )}
      </S.RoomListContainer>
      {hasNextPage && onLoadMore && <PlusButton onClick={onLoadMore} />}
    </S.RoomListSection>
  );
};

export default RoomList;
