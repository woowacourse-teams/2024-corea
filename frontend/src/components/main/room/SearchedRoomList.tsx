import { useFetchSearchRoomList } from "@/hooks/queries/useFetchRooms";
import RoomList from "@/components/shared/roomList/RoomList";
import * as S from "@/components/shared/roomList/RoomList.style";
import type { RoomStatusCategory } from "@/@types/roomInfo";
import { defaultCharacter } from "@/assets";
import convertClassification from "@/utils/converToClassification";

interface SearchedRoomListProps {
  roomType: RoomStatusCategory;
  searchInput: string;
  selectedCategory: string;
}

const SearchedRoomList = ({
  roomType,
  searchInput = "",
  selectedCategory,
}: SearchedRoomListProps) => {
  const { data } = useFetchSearchRoomList(
    roomType,
    convertClassification(selectedCategory),
    searchInput,
    true,
  );

  if (data?.rooms.length === 0) {
    return (
      <S.EmptyContainer>
        <S.Character src={defaultCharacter} alt="기본 캐릭터" />
        <p>검색 결과가 없습니다. 철자나 띄어쓰기를 다시 확인해주세요!</p>
      </S.EmptyContainer>
    );
  }

  return <RoomList roomList={data?.rooms ?? []} roomType={roomType} />;
};

export default SearchedRoomList;
