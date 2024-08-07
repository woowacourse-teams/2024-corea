import { useQuery } from "@tanstack/react-query";
import useModal from "@/hooks/common/useModal";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import ReviewerFeedbackModal from "@/components/feedback/reviewerFeedbackModal/ReviewerFeedbackModal";
import * as S from "@/components/roomDetailPage/myReviewer/MyReviewer.style";
import { RoomInfo } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import { getMyReviewers } from "@/apis/reviews.api";
import MESSAGES from "@/constants/message";

interface MyReviewerProps {
  roomInfo: RoomInfo;
}

const MyReviewer = ({ roomInfo }: MyReviewerProps) => {
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();

  const { data: reviewerData } = useQuery({
    queryKey: [QUERY_KEYS.REVIEWERS, roomInfo.id],
    queryFn: () => getMyReviewers(roomInfo.id),
  });

  if (!reviewerData || reviewerData.length === 0) {
    return <>{MESSAGES.GUIDANCE.EMPTY_REVIEWER}</>;
  }

  return (
    <>
      <ReviewerFeedbackModal
        isOpen={isOpen}
        onClose={handleCloseModal}
        roomInfo={roomInfo}
        buttonType="create"
      />

      <S.MyReviewerContainer>
        <S.MyReviewerWrapper>
          <S.MyReviewerTitle>아이디</S.MyReviewerTitle>
          <S.MyReviewerTitle>PR 링크</S.MyReviewerTitle>
          <S.MyReviewerTitle>제출 여부</S.MyReviewerTitle>
        </S.MyReviewerWrapper>

        {reviewerData.map((reviewer) => (
          <S.MyReviewerWrapper key={reviewer.userId}>
            <S.MyReviewerContent>{reviewer.username}</S.MyReviewerContent>
            <S.MyReviewerContent>
              <S.PRLink href={reviewer.link}>
                <Icon kind="link" />
                바로가기
              </S.PRLink>
            </S.MyReviewerContent>
            <S.MyReviewerContent>
              <Button size="small" onClick={handleOpenModal} variant="primary">
                피드백 작성
              </Button>
            </S.MyReviewerContent>
          </S.MyReviewerWrapper>
        ))}
      </S.MyReviewerContainer>
    </>
  );
};

export default MyReviewer;
