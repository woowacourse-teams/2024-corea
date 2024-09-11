import { useState } from "react";
import useModal from "@/hooks/common/useModal";
import { useFetchReviewer } from "@/hooks/queries/useFetchReviewer";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import ReviewerFeedbackModal from "@/components/feedback/reviewerFeedbackModal/ReviewerFeedbackModal";
import * as S from "@/components/roomDetailPage/myReviewer/MyReviewer.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";
import { FeedbackTypeResult, getFeedbackType } from "@/utils/feedbackUtils";

interface MyReviewerProps {
  roomInfo: RoomInfo;
}

const MyReviewer = ({ roomInfo }: MyReviewerProps) => {
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();
  const [selectedReviewer, setSelectedReviewer] = useState<ReviewerInfo | null>(null);
  const [feedbackTypeResult, setFeedbackTypeResult] = useState<FeedbackTypeResult | null>(null);

  const { data: reviewerData } = useFetchReviewer(roomInfo);

  if (reviewerData.length === 0) {
    return <S.ErrorWrapper>{MESSAGES.GUIDANCE.EMPTY_REVIEWER}</S.ErrorWrapper>;
  }

  const handleOpenFeedbackModal = (reviewer: ReviewerInfo) => {
    const result = getFeedbackType({
      isReviewed: reviewer.isReviewed,
      isWrited: reviewer.isWrited,
      isClosed: roomInfo.isClosed,
    });
    setSelectedReviewer(reviewer);
    setFeedbackTypeResult(result);
    handleOpenModal();
  };

  return (
    <>
      {selectedReviewer && feedbackTypeResult && (
        <ReviewerFeedbackModal
          key={selectedReviewer.username}
          isOpen={isOpen}
          onClose={() => {
            handleCloseModal();
            setSelectedReviewer(null);
            setFeedbackTypeResult(null);
          }}
          roomInfo={roomInfo}
          reviewer={selectedReviewer}
          modalType={feedbackTypeResult.modalType}
          buttonText={feedbackTypeResult.buttonText}
        />
      )}

      <S.MyReviewerContainer>
        <S.MyReviewerWrapper>
          <S.MyReviewerTitle>아이디</S.MyReviewerTitle>
          <S.MyReviewerTitle>PR 링크</S.MyReviewerTitle>
          <S.MyReviewerTitle>피드백 여부</S.MyReviewerTitle>
        </S.MyReviewerWrapper>

        {reviewerData.map((reviewer) => {
          const { buttonText } = getFeedbackType({
            isReviewed: reviewer.isReviewed,
            isWrited: reviewer.isWrited,
            isClosed: roomInfo.isClosed,
          });

          return (
            <S.MyReviewerWrapper key={reviewer.userId}>
              <S.MyReviewerContent>{reviewer.username}</S.MyReviewerContent>
              <S.MyReviewerContent>
                <S.PRLink href={reviewer.link}>
                  <S.IconWrapper>
                    <Icon kind="link" />
                  </S.IconWrapper>
                  바로가기
                </S.PRLink>
              </S.MyReviewerContent>

              <S.MyReviewerContent>
                <Button
                  size="xSmall"
                  onClick={() => handleOpenFeedbackModal(reviewer)}
                  variant={reviewer.isReviewed ? "secondary" : "disable"}
                  disabled={!reviewer.isReviewed}
                >
                  {buttonText}
                </Button>
              </S.MyReviewerContent>
            </S.MyReviewerWrapper>
          );
        })}
      </S.MyReviewerContainer>
    </>
  );
};

export default MyReviewer;
