import { useQuery } from "@tanstack/react-query";
import useSelectedCategory from "@/hooks/common/useSelectedCategory";
import useFetchParticipatedRoomList from "@/hooks/queries/rooms/useFetchParticipatedRoomList";
import { useInfiniteFetchRoomList } from "@/hooks/queries/rooms/useInfiniteFetchRoomList";
import ContentSection from "@/components/common/contentSection/ContentSection";
import MenuBar from "@/components/common/menuBar/MenuBar";
import RoomList from "@/components/shared/roomList/RoomList";
import * as S from "@/pages/main/MainPage.style";
import QUERY_KEYS from "@/apis/queryKeys";
import { getClosedRoomList, getOpenedRoomList, getParticipatedRoomList } from "@/apis/rooms.api";

const MainPage = () => {
  const { selectedCategory, handleSelectedCategory } = useSelectedCategory();

  const { data: participatedRoomList } = useFetchParticipatedRoomList();

  const {
    data: openedRoomList,
    fetchNextPage: fetchNextOpenedPage,
    hasNextPage: hasNextOpenedPage,
  } = useInfiniteFetchRoomList({
    queryKey: [QUERY_KEYS.OPENED_ROOM_LIST, selectedCategory],
    getRoomList: getOpenedRoomList,
    classification: selectedCategory,
  });

  const {
    data: closedRoomList,
    fetchNextPage: fetchNextClosedPage,
    hasNextPage: hasNextClosedPage,
  } = useInfiniteFetchRoomList({
    queryKey: [QUERY_KEYS.CLOSED_ROOM_LIST, selectedCategory],
    getRoomList: getClosedRoomList,
    classification: selectedCategory,
  });

  const openedRooms = openedRoomList?.pages.flatMap((page) => page.rooms) || [];
  const closedRooms = closedRoomList?.pages.flatMap((page) => page.rooms) || [];

  return (
    <S.Layout>
      <ContentSection title="참여 중인 방 리스트">
        {participatedRoomList && (
          <RoomList roomList={participatedRoomList.rooms} roomType="participated" />
        )}
      </ContentSection>

      <MenuBar selectedCategory={selectedCategory} onCategoryClick={handleSelectedCategory} />

      <ContentSection title="모집 중인 방 리스트">
        <RoomList
          roomList={openedRooms}
          hasNextPage={hasNextOpenedPage}
          onLoadMore={() => fetchNextOpenedPage()}
          roomType="opened"
        />
      </ContentSection>

      <ContentSection title="모집 마감된 방 리스트">
        <RoomList
          roomList={closedRooms}
          hasNextPage={hasNextClosedPage}
          onLoadMore={() => fetchNextClosedPage()}
          roomType="closed"
        />
      </ContentSection>
    </S.Layout>
  );
};

export default MainPage;
