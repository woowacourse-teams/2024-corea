import React, { useState } from "react";
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
  const { data: revieweeData } = useFetchReviewee(roomInfo);
  const { isModalOpen, handleOpenModal, handleCloseModal } = useModal();
  const { postReviewCompleteMutation } = useMutateReviewComplete(roomInfo.id);
  const [selectedReviewee, setSelectedReviewee] = useState<ReviewerInfo | null>(null);
  const [feedbackTypeResult, setFeedbackTypeResult] = useState<FeedbackTypeResult | null>(null);

  // 피드백 모달 여는 함수
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

  // 코드 리뷰 완료 post 요청 보내는 함수
  const handleReviewCompleteClick = (revieweeId: number) => {
    postReviewCompleteMutation.mutate({ roomId: roomInfo.id, revieweeId });
  };

  // 리뷰 및 피드백 여부 버튼 렌더링 함수
  const renderRevieweeButton = (reviewee: ReviewerInfo) => {
    const { buttonText } = getFeedbackType({
      isReviewed: reviewee.isReviewed,
      isWrited: reviewee.isWrited,
      isClosed: roomInfo.roomStatus === "CLOSE",
    });

    if (roomInfo.roomStatus === "CLOSE" && !reviewee.isReviewed) {
      return <p>코드리뷰를 하지 않았어요</p>;
    }

    return reviewee.isReviewed ? (
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
    );
  };

  // 매칭 전 보여줄 화면
  if (roomInfo.roomStatus === "OPEN" && revieweeData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-waiting">{MESSAGES.GUIDANCE.EMPTY_REVIEWEE}</p>
        <p className="process-waiting">{MESSAGES.GUIDANCE.SUB_DESCRIPTION}</p>
      </S.GuidanceWrapper>
    );
  }

  // 방 종료 후 실패했을 때 보여줄 화면
  if (roomInfo.roomStatus === "CLOSE" && revieweeData.length === 0) {
    return (
      <S.GuidanceWrapper>
        <p className="process-paused">
          {roomInfo.memberRole === "REVIEWER"
            ? MESSAGES.GUIDANCE.REVIEWEE_FAIL_MATCHED
            : MESSAGES.GUIDANCE.FAIL_MATCHED}
        </p>
      </S.GuidanceWrapper>
    );
  }

  // 매칭 후 성공했을 때 보여줄 화면
  return (
    <>
      {selectedReviewee && feedbackTypeResult && (
        <RevieweeFeedbackModal
          key={selectedReviewee.username}
          isOpen={isModalOpen}
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

        {revieweeData?.map((reviewee) => (
          <S.MyRevieweeWrapper key={reviewee.userId}>
            <HoverStyledLink to={`/profile/${reviewee.username}`}>
              <S.MyRevieweeContent>{reviewee.username}</S.MyRevieweeContent>
            </HoverStyledLink>

            <S.MyRevieweeContent>
              <S.PRLink href={reviewee.link} target="_blank">
                <S.IconWrapper>
                  <Icon kind="link" size="1.6rem" />
                </S.IconWrapper>
                바로가기
              </S.PRLink>
            </S.MyRevieweeContent>

            <S.MyRevieweeContent>
              <S.ButtonContainer>{renderRevieweeButton(reviewee)}</S.ButtonContainer>
            </S.MyRevieweeContent>
          </S.MyRevieweeWrapper>
        ))}
      </S.MyRevieweeContainer>
    </>
  );
};

export default MyReviewee;
