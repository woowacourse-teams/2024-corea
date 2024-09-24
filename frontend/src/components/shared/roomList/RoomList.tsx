import { Link } from "react-router-dom";
import PlusButton from "@/components/common/plusButton/PlusButton";
import WithSuspense from "@/components/common/withSuspense/WithSuspense";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as RoomCardSkeleton from "@/components/shared/roomCard/RoomCard.skeleton";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";

interface RoomListProps {
  roomList: RoomInfo[];
  isFetching: boolean;
  hasNextPage?: boolean;
  onLoadMore?: () => void;
  participated?: boolean;
}

const RoomList = ({
  roomList,
  isFetching,
  hasNextPage,
  onLoadMore,
  participated,
}: RoomListProps) => {
  const handleClickLoadMore = () => {
    if (isFetching) return;

    onLoadMore?.();
  };

  return (
    <S.RoomListSection>
      <S.RoomListContainer>
        {roomList.map((roomInfo) =>
          participated ? (
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
