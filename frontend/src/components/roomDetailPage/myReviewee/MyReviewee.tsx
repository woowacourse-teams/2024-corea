import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import useModal from "@/hooks/common/useModal";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import RevieweeFeedbackModal from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal";
import * as S from "@/components/roomDetailPage/myReviewee/MyReviewee.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import { getMyReviewees } from "@/apis/reviews.api";
import MESSAGES from "@/constants/message";
import { getFeedbackModalType } from "@/utils/feedbackUtils";

interface MyReviewerProps {
  roomInfo: RoomInfo;
}

const MyReviewee = ({ roomInfo }: MyReviewerProps) => {
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();
  const [selectedReviewee, setSelectedReviewee] = useState<ReviewerInfo | null>(null);

  const { data: revieweeData } = useQuery({
    queryKey: [QUERY_KEYS.REVIEWEES, roomInfo.id],
    queryFn: () => getMyReviewees(roomInfo.id),
  });

  if (!revieweeData || revieweeData.length === 0) {
    return <>{MESSAGES.GUIDANCE.EMPTY_REVIEWEE}</>;
  }

  const handleOpenFeedbackModal = (reviewee: ReviewerInfo) => {
    setSelectedReviewee(reviewee);
    handleOpenModal();
  };

  return (
    <>
      {selectedReviewee && (
        <RevieweeFeedbackModal
          isOpen={isOpen}
          onClose={handleCloseModal}
          roomInfo={roomInfo}
          reviewee={selectedReviewee}
        />
      )}

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
              <Button
                size="small"
                onClick={() => alert("버튼 클릭 완료!")}
                variant={reviewee.isReviewed ? "disable" : "secondary"}
                disabled={reviewee.isReviewed}
              >
                {reviewee.isReviewed ? "코드리뷰 완료" : "코드리뷰 하기"}
              </Button>
              <Button
                size="small"
                onClick={() => handleOpenFeedbackModal(reviewee)}
                variant={reviewee.isReviewed ? "primary" : "disable"}
                disabled={!reviewee.isReviewed}
              >
                {getFeedbackModalType({
                  isWrited: reviewee.isWrited,
                  isClosed: roomInfo.isClosed,
                })}
              </Button>
            </S.MyRevieweeContent>
          </S.MyRevieweeWrapper>
        ))}
      </S.MyRevieweeContainer>
    </>
  );
};

export default MyReviewee;
