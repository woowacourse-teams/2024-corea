import { Textarea } from "@/components/common/textarea/Textarea";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import * as S from "@/components/feedback/feedbackForm/FeedbackForm.style";
import KeywordOptionButton from "@/components/feedback/keywordOptionButton/KeywordOptionButton";
import { ReviewerFeedbackData } from "@/@types/feedback";
import { FeedbackModalType } from "@/utils/feedbackUtils";

interface ReviewerFeedbackFormProps {
  formState: ReviewerFeedbackData;
  onChange: (
    key: keyof ReviewerFeedbackData,
    value: ReviewerFeedbackData[keyof ReviewerFeedbackData],
  ) => void;
  modalType: FeedbackModalType;
}

const ReviewerFeedbackForm = ({ formState, onChange, modalType }: ReviewerFeedbackFormProps) => {
  return (
    <>
      <S.ItemContainer>
        <S.ModalQuestion required>
          리뷰어의 소프트 스킬 역량 향상을 위해 피드백을 해주세요.
        </S.ModalQuestion>
        <EvaluationPointBar
          initialOptionId={formState.evaluationPoint}
          onChange={(value) => onChange("evaluationPoint", value)}
          readonly={modalType === "view"}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.ModalQuestion required>위와 같이 선택한 이유를 알려주세요.</S.ModalQuestion>
        <KeywordOptionButton
          initialOptions={formState.feedbackKeywords}
          onChange={(value) => onChange("feedbackKeywords", value)}
          selectedEvaluationId={formState.evaluationPoint}
          readonly={modalType === "view"}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.ModalQuestion>추가적으로 하고 싶은 피드백이 있다면 남겨 주세요.</S.ModalQuestion>
        <Textarea
          rows={5}
          maxLength={512}
          placeholder="상대 리뷰어의 소프트 스킬 역량 향상을 위해 피드백을 남겨주세요."
          value={formState.feedbackText}
          onChange={(e) => onChange("feedbackText", e.target.value)}
          readOnly={modalType === "view"}
        />
      </S.ItemContainer>
    </>
  );
};

export default ReviewerFeedbackForm;
