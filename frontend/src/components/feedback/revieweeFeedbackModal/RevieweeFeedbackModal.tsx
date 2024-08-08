import React, { useEffect, useState } from "react";
import useMutateFeedback from "@/hooks/mutations/useMutateFeedback";
import { useFetchRevieweeFeedback } from "@/hooks/queries/useFetchFeedback";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import { Textarea } from "@/components/common/textarea/Textarea";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import OptionButton from "@/components/feedback/keywordOptionButton/KeywordOptionButton";
import RecommendationPointBar from "@/components/feedback/recommendationPointBar/RecommendationPointBar";
import * as S from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal.style";
import { RevieweeFeedbackData } from "@/@types/feedback";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { FeedbackModalType } from "@/utils/feedbackUtils";

type RevieweeFeedbackForm = Omit<RevieweeFeedbackData, "feedbackId" | "revieweeId">;

interface RevieweeFeedbackModalProps {
  isOpen: boolean;
  onClose: () => void;
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords" | "isClosed">;
  reviewee: ReviewerInfo;
  modalType: FeedbackModalType;
  buttonText: string;
}

const initialFormState: RevieweeFeedbackData = {
  feedbackId: 0,
  revieweeId: 0,
  evaluationPoint: 0,
  feedbackKeywords: [],
  feedbackText: "",
  recommendationPoint: 0,
};

const RevieweeFeedbackModal = ({
  isOpen,
  onClose,
  roomInfo,
  reviewee,
  modalType,
  buttonText,
}: RevieweeFeedbackModalProps) => {
  const {
    data: feedbackData,
    isLoading,
    error,
  } = useFetchRevieweeFeedback(roomInfo.id, reviewee.username);
  const { postRevieweeFeedbackMutation, putRevieweeFeedbackMutation } = useMutateFeedback();

  const [formState, setFormState] = useState<RevieweeFeedbackData>(initialFormState);

  useEffect(() => {
    if (isOpen) {
      if (modalType === "create") {
        setFormState({
          ...initialFormState,
          revieweeId: reviewee.userId,
        });
      } else if (feedbackData) {
        setFormState(feedbackData);
      }
    }
  }, [isOpen, modalType, feedbackData, reviewee.userId]);

  const handleClose = () => {
    setFormState(initialFormState);
    onClose();
  };

  const isFormValid =
    formState.evaluationPoint !== 0 &&
    formState.feedbackKeywords.length > 0 &&
    formState.recommendationPoint !== 0;

  const handleChange = (
    key: keyof RevieweeFeedbackForm,
    value: RevieweeFeedbackForm[keyof RevieweeFeedbackForm],
  ) => {
    if (modalType === "view") return;
    setFormState((prevState) => ({
      ...prevState,
      [key]: value,
    }));
  };

  const handleSubmit = () => {
    if (!isFormValid || modalType === "view") return;

    const feedbackData = {
      ...formState,
    };

    if (modalType === "create") {
      postRevieweeFeedbackMutation.mutate(
        { roomId: roomInfo.id, feedbackData },
        {
          onSuccess: () => {
            handleClose();
          },
        },
      );
    } else if (modalType === "edit") {
      putRevieweeFeedbackMutation.mutate(
        { roomId: roomInfo.id, feedbackId: feedbackData.feedbackId, feedbackData },
        {
          onSuccess: () => {
            handleClose();
          },
        },
      );
    }
  };

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

        <S.ItemContainer>
          <S.ModalQuestion required>
            리뷰이의 개발 역량 향상을 위해 코드를 평가 해주세요.
          </S.ModalQuestion>
          <EvaluationPointBar
            initialOptionId={formState.evaluationPoint}
            onChange={(value) => handleChange("evaluationPoint", value)}
            readonly={modalType === "view"}
          />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion required>어떤 점이 만족스러웠나요?</S.ModalQuestion>
          <OptionButton
            initialOptions={formState.feedbackKeywords}
            onChange={(value) => handleChange("feedbackKeywords", value)}
            selectedEvaluationId={formState.evaluationPoint}
            readonly={modalType === "view"}
          />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion>추가적으로 하고 싶은 피드백이 있다면 남겨 주세요.</S.ModalQuestion>
          <Textarea
            rows={5}
            maxLength={512}
            placeholder="상대 리뷰이의 개발 역량 향상을 위해 피드백을 남겨주세요."
            value={formState.feedbackText}
            onChange={(e) => handleChange("feedbackText", e.target.value)}
            readOnly={modalType === "view"}
          />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion required>리뷰이의 코드를 추천하시나요?</S.ModalQuestion>
          <RecommendationPointBar
            initialOptionId={formState.recommendationPoint}
            onChange={(value) => handleChange("recommendationPoint", value)}
            readonly={modalType === "view"}
          />
        </S.ItemContainer>

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
