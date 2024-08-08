import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import ContentSection from "@/components/common/contentSection/ContentSection";
import MyReviewee from "@/components/roomDetailPage/myReviewee/MyReviewee";
import MyReviewer from "@/components/roomDetailPage/myReviewer/MyReviewer";
import RoomInfoCard from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard";
import * as S from "@/pages/roomDetail/RoomDetailPage.style";
import QUERY_KEYS from "@/apis/queryKeys";
import { getRoomDetailInfo } from "@/apis/rooms.api";

const RoomDetailPage = () => {
  const params = useParams();
  const roomId = params.id ? Number(params.id) : 0;

  const { data: roomInfo } = useQuery({
    queryKey: [QUERY_KEYS.ROOM_DETAIL_INFO],
    queryFn: () => getRoomDetailInfo(roomId),
  });

  if (!roomInfo) return <></>;

  return (
    <S.Layout>
      <ContentSection title="미션 정보">
        <RoomInfoCard roomInfo={roomInfo} />
      </ContentSection>

      <ContentSection title="나를 리뷰해주는 분">
        <S.StyledDescription>
          리뷰어 피드백은 소프트 스킬 역량에 한해서 진행합니다.
        </S.StyledDescription>
        <MyReviewer roomInfo={roomInfo} />
      </ContentSection>

      <ContentSection title="내가 리뷰해야하는 분">
        <S.StyledDescription>리뷰이 피드백은 개발 역량에 한해서 진행합니다.</S.StyledDescription>
        <MyReviewee roomInfo={roomInfo} />
      </ContentSection>
    </S.Layout>
  );
};

export default RoomDetailPage;
