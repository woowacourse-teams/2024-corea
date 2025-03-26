import RoomListLoading from "../fallback/RoomListLoading";
import ClosedRoomList from "../room/ClosedRoomList";
import OpenedRoomList from "../room/OpenedRoomList";
import ParticipatedRoomList from "../room/ParticipatedRoomList";
import ProgressRoomList from "../room/ProgressRoomList";
import SearchedRoomList from "../room/SearchedRoomList";
import LocalSuspense from "@/components/common/localSuspense/LocalSuspense";
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
      <LocalSuspense fallback={<RoomListLoading />}>
        <SearchedRoomList
          roomType={tabToRoomType(selectedTab)}
          searchInput={searchInput}
          selectedCategory={selectedCategory}
        />
      </LocalSuspense>
    );
  }

  switch (selectedTab) {
    case "참여중":
      return (
        <LocalSuspense fallback={<RoomListLoading />}>
          <ParticipatedRoomList />
        </LocalSuspense>
      );
    case "모집중":
      return (
        <LocalSuspense fallback={<RoomListLoading />}>
          <OpenedRoomList selectedCategory={selectedCategory} />
        </LocalSuspense>
      );
    case "진행중":
      return (
        <LocalSuspense fallback={<RoomListLoading />}>
          <ProgressRoomList selectedCategory={selectedCategory} />
        </LocalSuspense>
      );
    case "종료됨":
      return (
        <LocalSuspense fallback={<RoomListLoading />}>
          <ClosedRoomList selectedCategory={selectedCategory} />
        </LocalSuspense>
      );
    default:
      return null;
  }
};

export default FilteredRoomList;
