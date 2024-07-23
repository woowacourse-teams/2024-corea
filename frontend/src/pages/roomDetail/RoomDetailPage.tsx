import ContentSection from "@/components/common/contentSection/ContentSection";
import MyReviewee from "@/components/roomDetailPage/myReviewee/MyReviewee";
import MyReviewer from "@/components/roomDetailPage/myReviewer/MyReviewer";
import RoomInfoCard from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard";
import * as S from "@/pages/roomDetail/RoomDetailPage.style";

const RoomDetailPage = () => {
  return (
    <S.Layout>
      <ContentSection title="미션 정보">
        <RoomInfoCard />
      </ContentSection>

      <ContentSection title="나의 리뷰어">
        <MyReviewer />
      </ContentSection>

      <ContentSection title="나의 리뷰이">
        <MyReviewee />
      </ContentSection>
    </S.Layout>
  );
};

export default RoomDetailPage;
