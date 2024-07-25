import { useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { useNextQuery } from "@/hooks/useNextQuery";
import ContentSection from "@/components/common/contentSection/ContentSection";
import MenuBar from "@/components/common/menuBar/MenuBar";
import RoomList from "@/components/shared/roomList/RoomList";
import * as S from "@/pages/main/MainPage.style";
import QUERY_KEYS from "@/apis/queryKeys";
import { getClosedRoomList, getOpenedRoomList, getParticipatedRoomList } from "@/apis/rooms.api";

const MainPage = () => {
  const [selectedCategory, setSelectedCategory] = useState(() => {
    return localStorage.getItem("selectedCategory") || "all";
  });

  useEffect(() => {
    localStorage.setItem("selectedCategory", selectedCategory);
  }, [selectedCategory]);

  const { data: participatedRoomList } = useQuery({
    queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST],
    queryFn: getParticipatedRoomList,
  });

  const {
    data: openedRoomList,
    fetchNextPage: fetchNextOpenedPage,
    hasNextPage: hasNextOpenedPage,
  } = useNextQuery({
    queryKey: [QUERY_KEYS.OPENED_ROOM_LIST, selectedCategory],
    getRoomList: getOpenedRoomList,
    classification: selectedCategory,
  });

  const {
    data: closedRoomList,
    fetchNextPage: fetchNextClosedPage,
    hasNextPage: hasNextClosedPage,
  } = useNextQuery({
    queryKey: [QUERY_KEYS.CLOSED_ROOM_LIST, selectedCategory],
    getRoomList: getClosedRoomList,
    classification: selectedCategory,
  });

  const openedRooms = openedRoomList?.pages.flatMap((page) => page.roomsInfo) || [];
  const closedRooms = closedRoomList?.pages.flatMap((page) => page.roomsInfo) || [];

  const handleCategoryClick = (category: string) => {
    setSelectedCategory(category);
  };

  if (!participatedRoomList) return <></>;

  return (
    <S.Layout>
      <ContentSection title="참여 중인 방 리스트">
        {participatedRoomList && <RoomList roomList={participatedRoomList.roomInfo} />}
      </ContentSection>

      <MenuBar selectedCategory={selectedCategory} onCategoryClick={handleCategoryClick} />

      <ContentSection title="모집 중인 방 리스트">
        <RoomList
          roomList={openedRooms}
          hasNextPage={hasNextOpenedPage}
          onLoadMore={() => fetchNextOpenedPage()}
        />
      </ContentSection>

      <ContentSection title="모집 마감된 방 리스트">
        <RoomList
          roomList={closedRooms}
          hasNextPage={hasNextClosedPage}
          onLoadMore={() => fetchNextClosedPage()}
        />
      </ContentSection>
    </S.Layout>
  );
};

export default MainPage;
