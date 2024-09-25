import { Link } from "react-router-dom";
import PlusButton from "@/components/common/plusButton/PlusButton";
import WithSuspense from "@/components/common/withSuspense/WithSuspense";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as RoomCardSkeleton from "@/components/shared/roomCard/RoomCard.skeleton";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";
import { defaultCharacter } from "@/assets";

interface RoomListProps {
  roomList: RoomInfo[];
  isFetching: boolean;
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

const RoomList = ({ roomList, isFetching, hasNextPage, onLoadMore, roomType }: RoomListProps) => {
  const handleClickLoadMore = () => {
    if (isFetching) return;

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
        {isFetching &&
          Array.from({ length: 8 }).map((_, idx) => <RoomCardSkeleton.Wrapper key={idx} />)}
      </S.RoomListContainer>
      {hasNextPage && onLoadMore && <PlusButton onClick={handleClickLoadMore} />}
    </S.RoomListSection>
  );
};

export default WithSuspense(RoomList, null);
