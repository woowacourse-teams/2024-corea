import { useReviewerFeedbackForm } from "@/hooks/feedback/useReviewerFeedbackForm";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import { ModalProps } from "@/components/common/modal/Modal";
import FeedbackCustomModal from "@/components/common/modal/feedbackCustomModal/FeedbackCustomModal";
import ReviewerFeedbackForm from "@/components/feedback/feedbackForm/ReviewerFeedbackForm";
import * as S from "@/components/feedback/reviewerFeedbackModal/ReviewerFeedbackModal.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { theme } from "@/styles/theme";
import { FeedbackModalType } from "@/utils/feedbackUtils";

interface ReviewerFeedbackModalProps extends ModalProps {
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords">;
  reviewer: ReviewerInfo;
  modalType: FeedbackModalType;
  buttonText: string;
}

const ReviewerFeedbackModal = ({
  isOpen,
  onClose,
  roomInfo,
  reviewer,
  modalType,
  buttonText,
}: ReviewerFeedbackModalProps) => {
  const { formState, handleChange, isFormValid, handleSubmit, handleClose } =
    useReviewerFeedbackForm(roomInfo.id, reviewer.username, reviewer.userId, modalType, onClose);

  const displayedKeywords = roomInfo.keywords.filter((keyword) => keyword !== "");

  return (
    <FeedbackCustomModal isOpen={isOpen} onClose={handleClose}>
      <S.FeedbackContainer>
        <S.ModalType>
          {modalType === "create" && "리뷰어 피드백 작성하기"}
          {modalType === "edit" && "리뷰어 피드백 수정하기"}
          {modalType === "view" && "리뷰어 피드백 확인하기"}
        </S.ModalType>
        <S.ModalTitle>{roomInfo.title}</S.ModalTitle>
        <S.Keywords>
          {displayedKeywords.length === 0 ? (
            <S.NoKeywordText>지정된 키워드 없음</S.NoKeywordText>
          ) : (
            displayedKeywords.map((keyword) => (
              <Label
                key={keyword}
                type="KEYWORD"
                text={keyword}
                size="semiSmall"
                backgroundColor={theme.COLOR.grey0}
              />
            ))
          )}
        </S.Keywords>

        <ReviewerFeedbackForm formState={formState} onChange={handleChange} modalType={modalType} />

        {modalType !== "view" && (
          <S.ButtonWrapper>
            <Button onClick={handleSubmit} disabled={!isFormValid}>
              {buttonText}
            </Button>
          </S.ButtonWrapper>
        )}
      </S.FeedbackContainer>
    </FeedbackCustomModal>
  );
};

export default ReviewerFeedbackModal;
