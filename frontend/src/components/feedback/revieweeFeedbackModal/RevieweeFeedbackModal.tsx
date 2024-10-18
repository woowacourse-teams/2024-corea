import { useRevieweeFeedbackForm } from "@/hooks/feedback/useRevieweeFeedbackForm";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import { ModalProps } from "@/components/common/modal/Modal";
import FeedbackCustomModal from "@/components/common/modal/feedbackCustomModal/FeedbackCustomModal";
import RevieweeFeedbackForm from "@/components/feedback/feedbackForm/RevieweeFeedbackForm";
import * as S from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { theme } from "@/styles/theme";
import { FeedbackModalType } from "@/utils/feedbackUtils";

interface RevieweeFeedbackModalProps extends ModalProps {
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords">;
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

  const displayedKeywords = roomInfo.keywords.filter((keyword) => keyword !== "");

  return (
    <FeedbackCustomModal isOpen={isOpen} onClose={handleClose}>
      <S.FeedbackContainer>
        <S.ModalType>
          {modalType === "create" && "리뷰이 피드백 작성하기"}
          {modalType === "edit" && "리뷰이 피드백 수정하기"}
          {modalType === "view" && "리뷰이 피드백 확인하기"}
        </S.ModalType>
        <S.FeedbackContainerHeader>
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
        </S.FeedbackContainerHeader>

        <RevieweeFeedbackForm formState={formState} onChange={handleChange} modalType={modalType} />

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

export default RevieweeFeedbackModal;
