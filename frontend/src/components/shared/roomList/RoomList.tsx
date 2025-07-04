import RoomListEmpty from "./RoomListEmpty";
import RoomListSkeleton from "./RoomListSkeleton";
import { Link } from "react-router-dom";
import PlusButton from "@/components/common/plusButton/PlusButton";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo, RoomStatusCategory } from "@/@types/roomInfo";

interface RoomListProps {
  roomList: RoomInfo[];
  isFetchingNextPage?: boolean;
  hasNextPage?: boolean;
  onLoadMore?: () => void;
  roomType: RoomStatusCategory;
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
    return <RoomListEmpty message={RoomEmptyText[roomType]} />;
  }

  return (
    <S.RoomListSection>
      <S.RoomListContainer aria-busy={isFetchingNextPage}>
        {roomList.map((roomInfo) =>
          roomType === "participated" ||
          roomInfo.participationStatus === "PARTICIPATED" ||
          roomInfo.participationStatus === "MANAGER" ? (
            <Link to={`/rooms/${roomInfo.id}`} key={roomInfo.id} tabIndex={-1}>
              <RoomCard roomInfo={roomInfo} />
            </Link>
          ) : (
            <RoomCard roomInfo={roomInfo} key={roomInfo.id} />
          ),
        )}

        {isFetchingNextPage && <RoomListSkeleton />}
      </S.RoomListContainer>

      {hasNextPage && onLoadMore && <PlusButton onClick={handleClickLoadMore} />}
    </S.RoomListSection>
  );
};

export default RoomList;
