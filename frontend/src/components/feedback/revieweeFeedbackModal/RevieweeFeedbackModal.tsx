import { useRevieweeFeedbackForm } from "@/hooks/feedback/useRevieweeFeedbackForm";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import RevieweeFeedbackForm from "@/components/feedback/feedbackForm/RevieweeFeedbackForm";
import * as S from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { FeedbackModalType } from "@/utils/feedbackUtils";

interface RevieweeFeedbackModalProps {
  isOpen: boolean;
  onClose: () => void;
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords" | "isClosed">;
  reviewee: ReviewerInfo;
  modalType: FeedbackModalType;
  buttonText: string;
}

const RevieweeFeedbackModal = ({
  isOpen,
  onClose,
  roomInfo,
  reviewee,
  modalType,
  buttonText,
}: RevieweeFeedbackModalProps) => {
  const { formState, handleChange, isFormValid, handleSubmit, handleClose } =
    useRevieweeFeedbackForm(roomInfo.id, reviewee.username, reviewee.userId, modalType, onClose);

  return (
    <Modal isOpen={isOpen} onClose={handleClose}>
      <S.FeedbackContainer>
        <S.ModalType>
          {modalType === "create" && "리뷰이 피드백 작성하기"}
          {modalType === "edit" && "리뷰이 피드백 수정하기"}
          {modalType === "view" && "리뷰이 피드백 확인하기"}
        </S.ModalType>
        <S.ModalTitle>{roomInfo.title}</S.ModalTitle>
        <S.Keywords>
          {roomInfo.keywords.map((keyword) => (
            <Label key={keyword} type="keyword" text={keyword} size="semiSmall" />
          ))}
        </S.Keywords>

        <RevieweeFeedbackForm formState={formState} onChange={handleChange} modalType={modalType} />

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

export default RevieweeFeedbackModal;
