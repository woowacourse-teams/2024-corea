import { Textarea } from "@/components/common/textarea/Textarea";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import * as S from "@/components/feedback/feedbackForm/FeedbackForm.style";
import KeywordOptionButton from "@/components/feedback/keywordOptionButton/KeywordOptionButton";
import { ReviewerFeedbackData } from "@/@types/feedback";
import {
  SOCIAL_BAD_KEYWORD_OPTIONS,
  SOCIAL_GOOD_KEYWORD_OPTIONS,
  SOCIAL_NORMAL_KEYWORD_OPTIONS,
} from "@/constants/feedback";
import { theme } from "@/styles/theme";
import { FeedbackModalType } from "@/utils/feedbackUtils";

interface ReviewerFeedbackFormProps {
  formState: ReviewerFeedbackData;
  onChange: (
    key: keyof ReviewerFeedbackData,
    value: ReviewerFeedbackData[keyof ReviewerFeedbackData],
  ) => void;
  modalType: FeedbackModalType;
}

const getSocialKeywordOptions = (selectedEvaluationId: number | undefined) => {
  if (selectedEvaluationId === undefined) return SOCIAL_GOOD_KEYWORD_OPTIONS;
  if (selectedEvaluationId <= 2) return SOCIAL_BAD_KEYWORD_OPTIONS;
  if (selectedEvaluationId === 3) return SOCIAL_NORMAL_KEYWORD_OPTIONS;
  return SOCIAL_GOOD_KEYWORD_OPTIONS;
};

const ReviewerFeedbackForm = ({ formState, onChange, modalType }: ReviewerFeedbackFormProps) => {
  return (
    <S.FeedbackFormContainer>
      <S.ItemContainer>
        <S.ModalQuestion>리뷰어의 소프트 스킬 역량 향상을 위해 피드백을 해주세요.</S.ModalQuestion>
        <S.Required>*필수입력</S.Required>
        <EvaluationPointBar
          initialOptionId={formState.evaluationPoint}
          onChange={(value) => onChange("evaluationPoint", value)}
          readonly={modalType === "view"}
          color={theme.COLOR.secondary}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.ModalQuestion>위와 같이 선택한 이유를 알려주세요. (1개 이상 선택)</S.ModalQuestion>
        <S.Required>*필수입력</S.Required>
        <KeywordOptionButton
          selectedOptions={formState.feedbackKeywords}
          onChange={(value) => onChange("feedbackKeywords", value)}
          selectedEvaluationId={formState.evaluationPoint}
          readonly={modalType === "view"}
          options={getSocialKeywordOptions(formState.evaluationPoint)}
          color={theme.COLOR.secondary}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.ModalQuestion>추가적으로 하고 싶은 피드백이 있다면 남겨 주세요.</S.ModalQuestion>
        <Textarea
          rows={8}
          showCharCount={true}
          maxLength={512}
          placeholder={
            modalType === "view"
              ? "없음"
              : "상대 리뷰어의 소프트스킬 향상을 위해 피드백을 남겨주세요."
          }
          value={formState.feedbackText}
          onChange={(e) => onChange("feedbackText", e.target.value)}
          readOnly={modalType === "view"}
        />
      </S.ItemContainer>
    </S.FeedbackFormContainer>
  );
};

export default ReviewerFeedbackForm;
