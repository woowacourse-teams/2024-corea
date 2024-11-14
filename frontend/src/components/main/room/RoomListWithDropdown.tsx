import { ChangeEvent, useEffect, useState } from "react";
import useToast from "@/hooks/common/useToast";
import { useFetchSearchRoomList } from "@/hooks/queries/useFetchRooms";
import ContentSection from "@/components/common/contentSection/ContentSection";
import Dropdown from "@/components/common/dropdown/Dropdown";
import SearchBar from "@/components/common/searchBar/SearchBar";
import * as S from "@/components/main/room/RoomList.style";
import RoomList from "@/components/shared/roomList/RoomList";
import { RoomInfo, RoomStatusCategory } from "@/@types/roomInfo";
import { dropdownItems } from "@/constants/roomDropdownItems";
import convertClassification from "@/utils/converToClassification";

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
  const { openToast } = useToast();

  const { refetch: fetchSearch, isLoading } = useFetchSearchRoomList(
    roomType,
    convertClassification(selectedCategory),
    searchInput,
    false,
  );

  const handleSearchInput = (e: ChangeEvent<HTMLInputElement>) => {
    setSearchInput(e.target.value);
  };

  const handleSearch = async () => {
    const { data } = await fetchSearch();
    if (!data || data.rooms.length === 0) {
      openToast("검색한 방이 없습니다.");
      return;
    }

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
        <RoomList roomList={searchedRooms} isFetchingNextPage={isLoading} roomType={roomType} />
      )}
    </ContentSection>
  );
};

export default RoomListWithDropdown;
