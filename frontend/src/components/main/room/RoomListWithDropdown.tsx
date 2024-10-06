import ContentSection from "@/components/common/contentSection/ContentSection";
import Dropdown from "@/components/common/dropdown/Dropdown";
import * as S from "@/components/main/room/RoomList.style";
import RoomList from "@/components/shared/roomList/RoomList";
import { RoomInfo } from "@/@types/roomInfo";
import { dropdownItems } from "@/constants/roomDropdownItems";

interface RoomListWithDropdownProps {
  selectedCategory: string;
  handleSelectedCategory: (category: string) => void;
  roomList: RoomInfo[];
  hasNextPage: boolean;
  onLoadMore: () => void;
  isFetchingNextPage: boolean;
  roomType: "participated" | "progress" | "opened" | "closed";
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
  return (
    <ContentSection title="">
      <S.DropdownWrapper>
        <Dropdown
          dropdownItems={dropdownItems}
          selectedCategory={selectedCategory}
          onSelectCategory={handleSelectedCategory}
        />
      </S.DropdownWrapper>
      <RoomList
        roomList={roomList}
        hasNextPage={hasNextPage}
        onLoadMore={onLoadMore}
        isFetchingNextPage={isFetchingNextPage}
        roomType={roomType}
      />
    </ContentSection>
  );
};

export default RoomListWithDropdown;
