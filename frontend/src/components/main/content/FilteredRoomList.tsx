import ClosedRoomList from "../room/ClosedRoomList";
import OpenedRoomList from "../room/OpenedRoomList";
import ParticipatedRoomList from "../room/ParticipatedRoomList";
import ProgressRoomList from "../room/ProgressRoomList";
import SearchedRoomList from "../room/SearchedRoomList";
import type { RoomStatusCategory } from "@/@types/roomInfo";
import type { Option } from "@/@types/rooms";

interface FilteredRoomListProps {
  selectedTab: Option;
  selectedCategory: string;
  searchInput: string;
}

const FilteredRoomList = ({
  selectedTab,
  selectedCategory,
  searchInput,
}: FilteredRoomListProps) => {
  const tabToRoomType = (tab: Option): RoomStatusCategory => {
    switch (tab) {
      case "참여중":
        return "participated";
      case "모집중":
        return "opened";
      case "진행중":
        return "progress";
      case "종료됨":
        return "closed";
    }
  };

  if (searchInput.trim()) {
    return (
      <SearchedRoomList
        roomType={tabToRoomType(selectedTab)}
        searchInput={searchInput}
        selectedCategory={selectedCategory}
      />
    );
  }

  switch (selectedTab) {
    case "참여중":
      return <ParticipatedRoomList />;
    case "진행중":
      return <ProgressRoomList selectedCategory={selectedCategory} />;
    case "모집중":
      return <OpenedRoomList selectedCategory={selectedCategory} />;
    case "종료됨":
      return <ClosedRoomList selectedCategory={selectedCategory} />;
    default:
      return null;
  }
};

export default FilteredRoomList;
