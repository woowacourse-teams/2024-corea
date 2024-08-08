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
import { FeedbackTypeResult, getFeedbackType } from "@/utils/feedbackUtils";

interface MyReviewerProps {
  roomInfo: RoomInfo;
}

const MyReviewee = ({ roomInfo }: MyReviewerProps) => {
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();
  const [selectedReviewee, setSelectedReviewee] = useState<ReviewerInfo | null>(null);
  const [feedbackTypeResult, setFeedbackTypeResult] = useState<FeedbackTypeResult | null>(null);

  const { data: revieweeData } = useQuery({
    queryKey: [QUERY_KEYS.REVIEWEES, roomInfo.id],
    queryFn: () => getMyReviewees(roomInfo.id),
  });

  if (!revieweeData || revieweeData.length === 0) {
    return <>{MESSAGES.GUIDANCE.EMPTY_REVIEWEE}</>;
  }

  const handleOpenFeedbackModal = (reviewee: ReviewerInfo) => {
    const result = getFeedbackType({
      isWrited: reviewee.isWrited,
      isClosed: roomInfo.isClosed,
    });
    setSelectedReviewee(reviewee);
    setFeedbackTypeResult(result);
    handleOpenModal();
  };

  return (
    <>
      {selectedReviewee && feedbackTypeResult && (
        <RevieweeFeedbackModal
          key={selectedReviewee.username}
          isOpen={isOpen}
          onClose={() => {
            handleCloseModal();
            setSelectedReviewee(null);
            setFeedbackTypeResult(null);
          }}
          roomInfo={roomInfo}
          reviewee={selectedReviewee}
          modalType={feedbackTypeResult.modalType}
          buttonText={feedbackTypeResult.buttonText}
        />
      )}

      <S.MyRevieweeContainer>
        <S.MyRevieweeWrapper>
          <S.MyRevieweeTitle>아이디</S.MyRevieweeTitle>
          <S.MyRevieweeTitle>PR 링크</S.MyRevieweeTitle>
          <S.MyRevieweeTitle>제출 여부</S.MyRevieweeTitle>
        </S.MyRevieweeWrapper>

        {revieweeData?.map((reviewee) => {
          const { buttonText } = getFeedbackType({
            isWrited: reviewee.isWrited,
            isClosed: roomInfo.isClosed,
          });

          return (
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
                  {buttonText}
                </Button>
              </S.MyRevieweeContent>
            </S.MyRevieweeWrapper>
          );
        })}
      </S.MyRevieweeContainer>
    </>
  );
};

export default MyReviewee;
