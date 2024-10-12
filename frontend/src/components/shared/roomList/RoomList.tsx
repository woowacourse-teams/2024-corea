import { Link } from "react-router-dom";
import DelaySuspense from "@/components/common/delaySuspense/DelaySuspense";
import PlusButton from "@/components/common/plusButton/PlusButton";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as RoomCardSkeleton from "@/components/shared/roomCard/RoomCard.skeleton";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";
import { defaultCharacter } from "@/assets";

interface RoomListProps {
  roomList: RoomInfo[];
  isFetchingNextPage: boolean;
  hasNextPage?: boolean;
  onLoadMore?: () => void;
  roomType: "participated" | "progress" | "opened" | "closed";
}

const RoomEmptyText = {
  participated: "참여한 방이 없습니다.",
  progress: "진행 중인 방이 없습니다.",
  opened: "모집 중인 방이 없습니다.",
  closed: "종료된 방이 없습니다.",
};

const RoomList = ({
  roomList,
  isFetchingNextPage,
  hasNextPage,
  onLoadMore,
  roomType,
}: RoomListProps) => {
  const handleClickLoadMore = () => {
    if (isFetchingNextPage) return;

    onLoadMore?.();
  };

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
        {isFetchingNextPage && (
          <DelaySuspense>
            {Array.from({ length: 8 }).map((_, idx) => (
              <RoomCardSkeleton.Wrapper key={idx} />
            ))}
          </DelaySuspense>
        )}
      </S.RoomListContainer>
      {hasNextPage && onLoadMore && <PlusButton onClick={handleClickLoadMore} />}
    </S.RoomListSection>
  );
};

export default RoomList;
