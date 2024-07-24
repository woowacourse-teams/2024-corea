import { useQuery } from "@tanstack/react-query";
import ContentSection from "@/components/common/contentSection/ContentSection";
import RoomList from "@/components/shared/roomList/RoomList";
import * as S from "@/pages/main/MainPage.style";
import QUERY_KEYS from "@/apis/queryKeys";
import { getParticipatedRoomList } from "@/apis/rooms.api";

const MainPage = () => {
  const { data: participatedRoomList } = useQuery({
    queryKey: [QUERY_KEYS.ROOM_DETAIL_INFO],
    queryFn: getParticipatedRoomList,
  });

  if (!participatedRoomList) return <></>;

  return (
    <S.Layout>
      <ContentSection title="참여 중인 방 리스트">
        {participatedRoomList && <RoomList roomList={participatedRoomList.roomInfo} />}
      </ContentSection>
    </S.Layout>
  );
};

export default MainPage;
