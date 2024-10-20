import { Textarea } from "@/components/common/textarea/Textarea";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import * as S from "@/components/feedback/feedbackForm/FeedbackForm.style";
import KeywordOptionButton from "@/components/feedback/keywordOptionButton/KeywordOptionButton";
import RecommendationPointBar from "@/components/feedback/recommendationPointBar/RecommendationPointBar";
import { RevieweeFeedbackData } from "@/@types/feedback";
import {
  DEVELOP_BAD_KEYWORD_OPTIONS,
  DEVELOP_GOOD_KEYWORD_OPTIONS,
  DEVELOP_NORMAL_KEYWORD_OPTIONS,
} from "@/constants/feedback";
import { FeedbackModalType } from "@/utils/feedbackUtils";

interface RevieweeFeedbackFormProps {
  formState: RevieweeFeedbackData;
  onChange: (
    key: keyof RevieweeFeedbackData,
    value: RevieweeFeedbackData[keyof RevieweeFeedbackData],
  ) => void;
  modalType: FeedbackModalType;
}

const getDevelopKeywordOptions = (selectedEvaluationId: number | undefined) => {
  if (selectedEvaluationId === undefined) return DEVELOP_GOOD_KEYWORD_OPTIONS;
  if (selectedEvaluationId <= 2) return DEVELOP_BAD_KEYWORD_OPTIONS;
  if (selectedEvaluationId === 3) return DEVELOP_NORMAL_KEYWORD_OPTIONS;
  return DEVELOP_GOOD_KEYWORD_OPTIONS;
};

const RevieweeFeedbackForm = ({ formState, onChange, modalType }: RevieweeFeedbackFormProps) => {
  return (
    <S.FeedbackFormContainer>
      <S.ItemContainer>
        <S.ModalQuestion>
          리뷰이의 개발 역량 향상을 위해 코드를 평가 해주세요.
          <span>*필수입력</span>
        </S.ModalQuestion>
        <EvaluationPointBar
          initialOptionId={formState.evaluationPoint}
          onChange={(value) => onChange("evaluationPoint", value)}
          readonly={modalType === "view"}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.ModalQuestion>
          위와 같이 선택한 이유를 알려주세요. (1개 이상 선택)
          <span>*필수입력</span>
        </S.ModalQuestion>
        <KeywordOptionButton
          selectedOptions={formState.feedbackKeywords}
          onChange={(value) => onChange("feedbackKeywords", value)}
          selectedEvaluationId={formState.evaluationPoint}
          readonly={modalType === "view"}
          options={getDevelopKeywordOptions(formState.evaluationPoint)}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.ModalQuestion>
          리뷰이의 코드를 추천하시나요?
          <span>*필수입력</span>
        </S.ModalQuestion>
        <RecommendationPointBar
          initialOptionId={formState.recommendationPoint}
          onChange={(value) => onChange("recommendationPoint", value)}
          readonly={modalType === "view"}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.ModalQuestion>추가적으로 하고 싶은 피드백이 있다면 남겨 주세요.</S.ModalQuestion>
        {modalType === "view" ? (
          <S.StyledTextarea>{formState.feedbackText}</S.StyledTextarea>
        ) : (
          <Textarea
            rows={10}
            showCharCount={true}
            maxLength={512}
            placeholder="상대 리뷰이의 개발 역량 향상을 위해 피드백을 남겨주세요."
            value={formState.feedbackText}
            onChange={(e) => onChange("feedbackText", e.target.value)}
          />
        )}
      </S.ItemContainer>
    </S.FeedbackFormContainer>
  );
};

export default RevieweeFeedbackForm;
