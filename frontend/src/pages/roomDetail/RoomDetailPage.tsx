import ContentForm from "@/components/common/contentForm/ContentForm";
import MyReviewee from "@/components/roomDetailPage/myReviewee/MyReviewee";
import MyReviewer from "@/components/roomDetailPage/myReviewer/MyReviewer";
import RoomInfoCard from "@/components/roomDetailPage/roomInfoCard/RoomInfoCard";
import * as S from "@/pages/roomDetail/RoomDetailPage.style";

const RoomDetailPage = () => {
  return (
    <S.Layout>
      <ContentForm title="미션 정보">
        <RoomInfoCard />
      </ContentForm>

      <ContentForm title="나의 리뷰어">
        <MyReviewer />
      </ContentForm>
      <ContentForm title="나의 리뷰이">
        <MyReviewee />
      </ContentForm>
    </S.Layout>
  );
};

export default RoomDetailPage;
