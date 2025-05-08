import RoomListError from "../fallback/RoomListError";
import RoomListLoading from "../fallback/RoomListLoading";
import ClosedRoomList from "../room/ClosedRoomList";
import OpenedRoomList from "../room/OpenedRoomList";
import ParticipatedRoomList from "../room/ParticipatedRoomList";
import ProgressRoomList from "../room/ProgressRoomList";
import SearchedRoomList from "../room/SearchedRoomList";
import LocalErrorBoundary from "@/components/common/localErrorBoundary/LocalErrorBoundary";
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
      <LocalErrorBoundary
        resetKeys={[selectedTab, selectedCategory, searchInput]}
        fallback={({ error, resetError }) => (
          <RoomListError message={error.message} onRetry={resetError} />
        )}
      >
        <LocalSuspense fallback={<RoomListLoading />}>
          <SearchedRoomList
            roomType={tabToRoomType(selectedTab)}
            searchInput={searchInput}
            selectedCategory={selectedCategory}
          />
        </LocalSuspense>
      </LocalErrorBoundary>
    );
  }

  switch (selectedTab) {
    case "참여중":
      return (
        <LocalErrorBoundary
          resetKeys={[selectedTab, selectedCategory, searchInput]}
          fallback={({ error, resetError }) => (
            <RoomListError message={error.message} onRetry={resetError} />
          )}
        >
          <LocalSuspense fallback={<RoomListLoading />}>
            <ParticipatedRoomList />
          </LocalSuspense>
        </LocalErrorBoundary>
      );
    case "모집중":
      return (
        <LocalErrorBoundary
          resetKeys={[selectedTab, selectedCategory, searchInput]}
          fallback={({ error, resetError }) => (
            <RoomListError message={error.message} onRetry={resetError} />
          )}
        >
          <LocalSuspense fallback={<RoomListLoading />}>
            <OpenedRoomList selectedCategory={selectedCategory} />
          </LocalSuspense>
        </LocalErrorBoundary>
      );
    case "진행중":
      return (
        <LocalErrorBoundary
          resetKeys={[selectedTab, selectedCategory, searchInput]}
          fallback={({ error, resetError }) => (
            <RoomListError message={error.message} onRetry={resetError} />
          )}
        >
          <LocalSuspense fallback={<RoomListLoading />}>
            <ProgressRoomList selectedCategory={selectedCategory} />
          </LocalSuspense>
        </LocalErrorBoundary>
      );
    case "종료됨":
      return (
        <LocalErrorBoundary
          resetKeys={[selectedTab, selectedCategory, searchInput]}
          fallback={({ error, resetError }) => (
            <RoomListError message={error.message} onRetry={resetError} />
          )}
        >
          <LocalSuspense fallback={<RoomListLoading />}>
            <ClosedRoomList selectedCategory={selectedCategory} />
          </LocalSuspense>
        </LocalErrorBoundary>
      );
    default:
      return null;
  }
};

export default FilteredRoomList;
