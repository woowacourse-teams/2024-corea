import { Link } from "react-router-dom";
import PlusButton from "@/components/common/plusButton/PlusButton";
import RoomCard from "@/components/shared/roomCard/RoomCard";
import * as S from "@/components/shared/roomList/RoomList.style";
import { RoomInfo } from "@/@types/roomInfo";

interface RoomListProps {
  roomList: RoomInfo[];
  hasNextPage?: boolean;
  onLoadMore?: () => void;
  roomType?: "participated" | "opened" | "closed";
}

const RoomList = ({ roomList, hasNextPage, onLoadMore, roomType }: RoomListProps) => {
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
