import { useReviewerFeedbackForm } from "@/hooks/feedback/useReviewerFeedbackForm";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import ReviewerFeedbackForm from "@/components/feedback/feedbackForm/ReviewerFeedbackForm";
import * as S from "@/components/feedback/reviewerFeedbackModal/ReviewerFeedbackModal.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { theme } from "@/styles/theme";
import { FeedbackModalType } from "@/utils/feedbackUtils";

interface ReviewerFeedbackModalProps {
  isOpen: boolean;
  onClose: () => void;
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords" | "isClosed">;
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

  return (
    <Modal isOpen={isOpen} onClose={handleClose}>
      <S.FeedbackContainer>
        <S.ModalType>
          {modalType === "create" && "리뷰어 피드백 작성하기"}
          {modalType === "edit" && "리뷰어 피드백 수정하기"}
          {modalType === "view" && "리뷰어 피드백 확인하기"}
        </S.ModalType>
        <S.ModalTitle>{roomInfo.title}</S.ModalTitle>
        <S.Keywords>
          {roomInfo.keywords.map((keyword) => (
            <Label
              key={keyword}
              type="keyword"
              text={keyword}
              size="semiSmall"
              backgroundColor={theme.COLOR.grey0}
            />
          ))}
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
    </Modal>
  );
};

export default ReviewerFeedbackModal;
