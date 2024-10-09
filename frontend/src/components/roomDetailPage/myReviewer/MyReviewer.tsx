import { useState } from "react";
import useModal from "@/hooks/common/useModal";
import { useFetchReviewer } from "@/hooks/queries/useFetchReviewer";
import Button from "@/components/common/button/Button";
import Icon from "@/components/common/icon/Icon";
import ReviewerFeedbackModal from "@/components/feedback/reviewerFeedbackModal/ReviewerFeedbackModal";
import * as S from "@/components/roomDetailPage/myReviewer/MyReviewer.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { thinkingCharacter } from "@/assets";
import MESSAGES from "@/constants/message";
import { HoverStyledLink } from "@/styles/common";
import { FeedbackTypeResult, getFeedbackType } from "@/utils/feedbackUtils";

interface MyReviewerProps {
  roomInfo: RoomInfo;
}

const MyReviewer = ({ roomInfo }: MyReviewerProps) => {
  const { isOpen, handleOpenModal, handleCloseModal } = useModal();
  const [selectedReviewer, setSelectedReviewer] = useState<ReviewerInfo | null>(null);
  const [feedbackTypeResult, setFeedbackTypeResult] = useState<FeedbackTypeResult | null>(null);

  const { data: reviewerData } = useFetchReviewer(roomInfo);

  if (roomInfo.memberRole === "REVIEWER") {
    return (
      <S.GuidanceWrapper>
        <S.Character src={thinkingCharacter} alt="생각 중인 캐릭터" />
        <p className="process-paused">{MESSAGES.GUIDANCE.ONLY_REVIEWER}</p>
      </S.GuidanceWrapper>
    );
  }

  if (reviewerData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-waiting">{MESSAGES.GUIDANCE.EMPTY_REVIEWER}</p>
        <p className="process-waiting">{MESSAGES.GUIDANCE.SUB_DESCRIPTION}</p>
      </S.GuidanceWrapper>
    );
  }

  const handleOpenFeedbackModal = (reviewer: ReviewerInfo) => {
    const feedbackType = getFeedbackType({
      isReviewed: reviewer.isReviewed,
      isWrited: reviewer.isWrited,
      isClosed: roomInfo.roomStatus === "CLOSE",
    });
    setSelectedReviewer(reviewer);
    setFeedbackTypeResult(feedbackType);
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
          <S.MyReviewerTitle>Comment 링크</S.MyReviewerTitle>
          <S.MyReviewerTitle>피드백 여부</S.MyReviewerTitle>
        </S.MyReviewerWrapper>

        {reviewerData.map((reviewer) => {
          const { buttonText } = getFeedbackType({
            isReviewed: reviewer.isReviewed,
            isWrited: reviewer.isWrited,
            isClosed: roomInfo.roomStatus === "CLOSE",
          });

          return (
            <S.MyReviewerWrapper key={reviewer.userId}>
              <HoverStyledLink to={`/profile/${reviewer.username}`}>
                <S.MyReviewerContent>{reviewer.username}</S.MyReviewerContent>
              </HoverStyledLink>

              {reviewer.link.length !== 0 ? (
                <S.MyReviewerContent>
                  <S.PRLink href={reviewer.link}>
                    <S.IconWrapper>
                      <Icon kind="link" size="1.6rem" />
                    </S.IconWrapper>
                    바로가기
                  </S.PRLink>
                </S.MyReviewerContent>
              ) : (
                "-"
              )}

              <S.MyReviewerContent>
                {reviewer.isReviewed ? (
                  <Button
                    size="xSmall"
                    onClick={() => handleOpenFeedbackModal(reviewer)}
                    variant={reviewer.isReviewed ? "secondary" : "disable"}
                    disabled={!reviewer.isReviewed}
                  >
                    {buttonText}
                  </Button>
                ) : (
                  <p>리뷰어가 리뷰 중이에요!</p>
                )}
              </S.MyReviewerContent>
            </S.MyReviewerWrapper>
          );
        })}
      </S.MyReviewerContainer>
    </>
  );
};

export default MyReviewer;
