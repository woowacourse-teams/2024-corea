import React, { useEffect, useState } from "react";
import Button from "@/components/common/button/Button";
import Label from "@/components/common/label/Label";
import Modal from "@/components/common/modal/Modal";
import { Textarea } from "@/components/common/textarea/Textarea";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import OptionButton from "@/components/feedback/optionButton/OptionButton";
import RecommendationPointBar from "@/components/feedback/recommendationPointBar/RecommendationPointBar";
import * as S from "@/components/feedback/revieweeFeedbackModal/RevieweeFeedbackModal.style";
import { ReviewerInfo } from "@/@types/reviewer";
import { RoomInfo } from "@/@types/roomInfo";
import { getFeedbackModalType } from "@/utils/feedbackUtils";

interface RevieweeFeedbackModalProps {
  isOpen: boolean;
  onClose: () => void;
  roomInfo: Pick<RoomInfo, "id" | "title" | "keywords" | "isClosed">;
  reviewee: ReviewerInfo;
}

const RevieweeFeedbackModal = ({
  isOpen,
  onClose,
  roomInfo,
  reviewee,
}: RevieweeFeedbackModalProps) => {
  const [evaluationPoint, setEvaluationPoint] = useState(0);
  const [feedbackKeywords, setFeedbackKeywords] = useState<string[]>([]);
  const [feedbackText, setFeedbackText] = useState("");
  const [recommendationPoint, setRecommendationPoint] = useState(0);
  const [isFormValid, setIsFormValid] = useState(false);

  useEffect(() => {
    const isValid =
      evaluationPoint !== 0 && feedbackKeywords.length > 0 && recommendationPoint !== 0;
    setIsFormValid(isValid);
  }, [evaluationPoint, feedbackKeywords, recommendationPoint]);

  const buttonText = getFeedbackModalType({
    isWrited: reviewee.isWrited,
    isClosed: roomInfo.isClosed,
  });

  const handleSubmit = () => {
    if (!isFormValid) return;

    onClose();
  };

  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <S.FeedbackContainer>
        <S.ModalType>리뷰이 피드백 작성하기</S.ModalType>
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
          <EvaluationPointBar initialOptionId={evaluationPoint} onChange={setEvaluationPoint} />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion required>어떤 점이 만족스러웠나요?</S.ModalQuestion>
          <OptionButton initialOptions={feedbackKeywords} onChange={setFeedbackKeywords} />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion>추가적으로 하고 싶은 피드백이 있다면 남겨 주세요.</S.ModalQuestion>
          <Textarea
            rows={5}
            maxLength={512}
            placeholder="상대 리뷰이의 개발 역량 향상을 위해 피드백을 남겨주세요."
            value={feedbackText}
            onChange={(e) => setFeedbackText(e.target.value)}
          />
        </S.ItemContainer>

        <S.ItemContainer>
          <S.ModalQuestion required>리뷰이의 코드를 추천하시나요?</S.ModalQuestion>
          <RecommendationPointBar
            initialOptionId={recommendationPoint}
            onChange={setRecommendationPoint}
          />
        </S.ItemContainer>

        <S.ButtonWrapper>
          <Button onClick={handleSubmit} disabled={!isFormValid}>
            {buttonText}
          </Button>
        </S.ButtonWrapper>
      </S.FeedbackContainer>
    </Modal>
  );
};

export default RevieweeFeedbackModal;
