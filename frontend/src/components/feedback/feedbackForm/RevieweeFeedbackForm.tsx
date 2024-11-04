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
import { FeedbackPageType } from "@/utils/feedbackUtils";

interface RevieweeFeedbackFormProps {
  formState: RevieweeFeedbackData;
  onChange: (
    key: keyof RevieweeFeedbackData,
    value: RevieweeFeedbackData[keyof RevieweeFeedbackData],
  ) => void;
  feedbackPageType: FeedbackPageType;
  isClicked: boolean;
}

const getDevelopKeywordOptions = (selectedEvaluationId: number | undefined) => {
  if (selectedEvaluationId === undefined) return DEVELOP_GOOD_KEYWORD_OPTIONS;
  if (selectedEvaluationId <= 2) return DEVELOP_BAD_KEYWORD_OPTIONS;
  if (selectedEvaluationId === 3) return DEVELOP_NORMAL_KEYWORD_OPTIONS;
  return DEVELOP_GOOD_KEYWORD_OPTIONS;
};

const RevieweeFeedbackForm = ({
  formState,
  onChange,
  feedbackPageType,
  isClicked,
}: RevieweeFeedbackFormProps) => {
  return (
    <S.FeedbackFormContainer>
      <S.ItemContainer>
        <S.QuestionContainer>
          <S.ModalQuestion $isInvalid={isClicked && formState.evaluationPoint === 0}>
            [공개] 리뷰이의 개발 역량 향상을 위해 코드를 평가 해주세요.
            <S.Required> *필수입력</S.Required>
          </S.ModalQuestion>
        </S.QuestionContainer>
        <EvaluationPointBar
          initialOptionId={formState.evaluationPoint}
          onChange={(value) => onChange("evaluationPoint", value)}
          readonly={feedbackPageType === "view"}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.QuestionContainer>
          <S.ModalQuestion $isInvalid={isClicked && formState.feedbackKeywords.length === 0}>
            [공개] 위와 같이 선택한 이유를 알려주세요. (복수선택 가능)
            <S.Required> *필수입력</S.Required>
          </S.ModalQuestion>
        </S.QuestionContainer>
        <KeywordOptionButton
          selectedOptions={formState.feedbackKeywords}
          onChange={(value) => onChange("feedbackKeywords", value)}
          selectedEvaluationId={formState.evaluationPoint}
          readonly={feedbackPageType === "view"}
          options={getDevelopKeywordOptions(formState.evaluationPoint)}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.QuestionContainer>
          <S.ModalQuestion>
            [비공개] 리뷰이의 코드를 추천하시나요?
            <S.Required> *필수입력</S.Required>
          </S.ModalQuestion>
        </S.QuestionContainer>
        <RecommendationPointBar
          initialOptionId={formState.recommendationPoint}
          onChange={(value) => onChange("recommendationPoint", value)}
          readonly={feedbackPageType === "view"}
        />
      </S.ItemContainer>

      <S.ItemContainer>
        <S.ModalQuestion>[공개] 추가적으로 하고 싶은 피드백이 있다면 남겨주세요.</S.ModalQuestion>
        <S.TextareaWrapper>
          <Textarea
            rows={10}
            showCharCount={true}
            maxLength={2000}
            placeholder={
              feedbackPageType === "view"
                ? "미작성"
                : "상대 리뷰이의 개발 역량 향상을 위해 피드백을 남겨주세요."
            }
            value={formState.feedbackText}
            onChange={(e) => onChange("feedbackText", e.target.value)}
            readOnly={feedbackPageType === "view"}
          />
        </S.TextareaWrapper>
      </S.ItemContainer>
    </S.FeedbackFormContainer>
  );
};

export default RevieweeFeedbackForm;
