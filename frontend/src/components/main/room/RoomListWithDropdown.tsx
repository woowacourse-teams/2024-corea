import { ChangeEvent, useEffect, useState } from "react";
import { useFetchSearchRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Dropdown from "@/components/common/dropdown/Dropdown";
import SearchBar from "@/components/common/searchBar/SearchBar";
import * as S from "@/components/main/room/RoomList.style";
import RoomList from "@/components/shared/roomList/RoomList";
import { Classification, RoomInfo, RoomStatusCategory } from "@/@types/roomInfo";
import { dropdownItems } from "@/constants/roomDropdownItems";

interface RoomListWithDropdownProps {
  selectedCategory: string;
  handleSelectedCategory: (category: string) => void;
  roomList: RoomInfo[];
  hasNextPage: boolean;
  onLoadMore: () => void;
  isFetchingNextPage: boolean;
  roomType: RoomStatusCategory;
}

const RoomListWithDropdown = ({
  selectedCategory,
  handleSelectedCategory,
  roomList,
  hasNextPage,
  onLoadMore,
  isFetchingNextPage,
  roomType,
}: RoomListWithDropdownProps) => {
  const [searchInput, setSearchInput] = useState("");
  const [searchedRooms, setSearchedRooms] = useState<RoomInfo[]>([]);

  const { refetch: fetchSearch, isLoading } = useFetchSearchRoomList(
    roomType,
    selectedCategory as Classification,
    searchInput,
    false,
  );

  const handleSearchInput = (e: ChangeEvent<HTMLInputElement>) => {
    setSearchInput(e.target.value);
  };

  const handleSearch = async () => {
    const { data } = await fetchSearch();
    if (!data) return;

    setSearchedRooms(data.rooms);
  };

  useEffect(() => {
    setSearchInput("");
    setSearchedRooms([]);
  }, [selectedCategory, roomType]);

  return (
    <ContentSection title="">
      <S.FilterWrapper>
        <Dropdown
          name="포지션 분류"
          dropdownItems={dropdownItems}
          selectedCategory={selectedCategory}
          onSelectCategory={handleSelectedCategory}
        />
        <S.SearchBarWrapper>
          <SearchBar
            value={searchInput}
            handleValue={handleSearchInput}
            handleSearch={handleSearch}
            placeholder="제목을 입력해주세요"
          />
        </S.SearchBarWrapper>
      </S.FilterWrapper>
      {searchedRooms.length === 0 ? (
        <RoomList
          roomList={roomList}
          hasNextPage={hasNextPage}
          onLoadMore={onLoadMore}
          isFetchingNextPage={isFetchingNextPage}
          roomType={roomType}
        />
      ) : (
        <RoomList
          roomList={searchedRooms}
          isFetchingNextPage={isLoading}
          roomType={roomType}
          emptyMessage="검색된 방이 없습니다."
        />
      )}
    </ContentSection>
  );
};

export default RoomListWithDropdown;
