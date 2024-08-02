import { useQuery } from "@tanstack/react-query";
import useModal from "@/hooks/common/useModal";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import RevieweeFeedbackModal from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal";
import * as S from "@/components/roomDetailPage/myReviewee/MyReviewee.style";
import { RoomInfo } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import { getMyReviewees } from "@/apis/reviews.api";
import MESSAGES from "@/constants/message";

interface MyReviewerProps {
  roomInfo: RoomInfo;
}

const MyReviewee = ({ roomInfo }: MyReviewerProps) => {
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();

  const { data: revieweeData } = useQuery({
    queryKey: [QUERY_KEYS.REVIEWEES, roomInfo.id],
    queryFn: () => getMyReviewees(roomInfo.id),
  });

  if (!revieweeData || revieweeData.length === 0) {
    return <>{MESSAGES.GUIDANCE.EMPTY_REVIEWEE}</>;
  }

  return (
    <>
      <RevieweeFeedbackModal
        isOpen={isOpen}
        onClose={handleCloseModal}
        roomInfo={roomInfo}
        buttonType="create"
      />

      <S.MyRevieweeContainer>
        <S.MyRevieweeWrapper>
          <S.MyRevieweeTitle>아이디</S.MyRevieweeTitle>
          <S.MyRevieweeTitle>PR 링크</S.MyRevieweeTitle>
          <S.MyRevieweeTitle>제출 여부</S.MyRevieweeTitle>
        </S.MyRevieweeWrapper>

        {revieweeData?.map((reviewee) => (
          <S.MyRevieweeWrapper key={reviewee.userId}>
            <S.MyRevieweeContent>{reviewee.username}</S.MyRevieweeContent>
            <S.MyRevieweeContent>
              <S.PRLink href={reviewee.link}>
                <Icon kind="link" />
                바로가기
              </S.PRLink>
            </S.MyRevieweeContent>
            <S.MyRevieweeContent>
              <Button size="small" onClick={() => alert("버튼 클릭 완료!")} variant="secondary">
                리뷰 완료
              </Button>
              <Button size="small" onClick={handleOpenModal} variant="primary">
                피드백 작성
              </Button>
            </S.MyRevieweeContent>
          </S.MyRevieweeWrapper>
        ))}
      </S.MyRevieweeContainer>
    </>
  );
};

export default MyReviewee;
