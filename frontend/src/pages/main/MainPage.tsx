import { useInfiniteQuery, useQuery } from "@tanstack/react-query";
import ContentSection from "@/components/common/contentSection/ContentSection";
import RoomList from "@/components/shared/roomList/RoomList";
import * as S from "@/pages/main/MainPage.style";
import QUERY_KEYS from "@/apis/queryKeys";
import { getClosedRoomList, getOpenedRoomList, getParticipatedRoomList } from "@/apis/rooms.api";

const MainPage = () => {
  const { data: participatedRoomList } = useQuery({
    queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST],
    queryFn: getParticipatedRoomList,
  });

  const {
    data: openedRoomList,
    fetchNextPage: fetchNextOpenedPage,
    hasNextPage: hasNextOpenedPage,
  } = useInfiniteQuery({
    queryKey: [QUERY_KEYS.OPENED_ROOM_LIST],
    queryFn: ({ pageParam = 1 }) => getOpenedRoomList("all", pageParam),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.isLastPage ? undefined : allPages.length + 1,
    initialPageParam: 1,
  });

  const {
    data: closedRoomList,
    fetchNextPage: fetchNextClosedPage,
    hasNextPage: hasNextClosedPage,
  } = useInfiniteQuery({
    queryKey: [QUERY_KEYS.CLOSED_ROOM_LIST],
    queryFn: ({ pageParam = 1 }) => getClosedRoomList("all", pageParam),
    getNextPageParam: (lastPage, allPages) =>
      lastPage.isLastPage ? undefined : allPages.length + 1,
    initialPageParam: 1,
  });

  const openedRooms = openedRoomList?.pages.flatMap((page) => page.roomInfo) || [];
  const closedRooms = closedRoomList?.pages.flatMap((page) => page.roomInfo) || [];

  if (!participatedRoomList) return <></>;

  return (
    <S.Layout>
      <ContentSection title="참여 중인 방 리스트">
        {participatedRoomList && <RoomList roomList={participatedRoomList.roomInfo} />}
      </ContentSection>

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
