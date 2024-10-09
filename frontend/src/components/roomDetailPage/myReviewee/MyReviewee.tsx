import { useState } from "react";
import useModal from "@/hooks/common/useModal";
import useMutateReviewComplete from "@/hooks/mutations/useMutateReview";
import { useFetchReviewee } from "@/hooks/queries/useFetchReviewee";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import RevieweeFeedbackModal from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal";
import * as S from "@/components/roomDetailPage/myReviewee/MyReviewee.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";
import { HoverStyledLink } from "@/styles/common";
import { FeedbackTypeResult, getFeedbackType } from "@/utils/feedbackUtils";

interface MyReviewerProps {
  roomInfo: RoomInfo;
}

const MyReviewee = ({ roomInfo }: MyReviewerProps) => {
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();
  const { postReviewCompleteMutation } = useMutateReviewComplete(roomInfo.id);
  const [selectedReviewee, setSelectedReviewee] = useState<ReviewerInfo | null>(null);
  const [feedbackTypeResult, setFeedbackTypeResult] = useState<FeedbackTypeResult | null>(null);

  const { data: revieweeData } = useFetchReviewee(roomInfo);

  if (revieweeData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-waiting">{MESSAGES.GUIDANCE.EMPTY_REVIEWEE}</p>
        <p className="process-waiting">{MESSAGES.GUIDANCE.SUB_DESCRIPTION}</p>
      </S.GuidanceWrapper>
    );
  }

  const handleOpenFeedbackModal = (reviewee: ReviewerInfo) => {
    const feedbackType = getFeedbackType({
      isReviewed: reviewee.isReviewed,
      isWrited: reviewee.isWrited,
      isClosed: roomInfo.roomStatus === "CLOSE",
    });
    setSelectedReviewee(reviewee);
    setFeedbackTypeResult(feedbackType);
    handleOpenModal();
  };

  const handleReviewCompleteClick = (revieweeId: number) => {
    postReviewCompleteMutation.mutate({ roomId: roomInfo.id, revieweeId });
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
          <S.MyRevieweeTitle>리뷰 및 피드백 여부</S.MyRevieweeTitle>
        </S.MyRevieweeWrapper>

        {revieweeData?.map((reviewee) => {
          const { buttonText } = getFeedbackType({
            isReviewed: reviewee.isReviewed,
            isWrited: reviewee.isWrited,
            isClosed: roomInfo.roomStatus === "CLOSE",
          });

          return (
            <S.MyRevieweeWrapper key={reviewee.userId}>
              <HoverStyledLink to={`/profile/${reviewee.username}`}>
                <S.MyRevieweeContent>{reviewee.username}</S.MyRevieweeContent>
              </HoverStyledLink>

              <S.MyRevieweeContent>
                <S.PRLink href={reviewee.link}>
                  <S.IconWrapper>
                    <Icon kind="link" size="1.6rem" />
                  </S.IconWrapper>
                  바로가기
                </S.PRLink>
              </S.MyRevieweeContent>

              <S.MyRevieweeContent>
                <S.ButtonContainer>
                  {reviewee.isReviewed ? (
                    <Button
                      size="xSmall"
                      variant="primary"
                      disabled={!reviewee.isReviewed}
                      onClick={() => handleOpenFeedbackModal(reviewee)}
                    >
                      {buttonText}
                    </Button>
                  ) : (
                    <Button
                      size="xSmall"
                      variant="confirm"
                      disabled={reviewee.isReviewed}
                      onClick={() => handleReviewCompleteClick(reviewee.userId)}
                    >
                      코드리뷰 완료
                    </Button>
                  )}
                </S.ButtonContainer>
              </S.MyRevieweeContent>
            </S.MyRevieweeWrapper>
          );
        })}
      </S.MyRevieweeContainer>
    </>
  );
};

export default MyReviewee;
