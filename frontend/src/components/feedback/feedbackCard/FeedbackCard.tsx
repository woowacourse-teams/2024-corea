import * as S from "./FeedbackCard.style";
import Button from "@/components/common/button/Button";
import Profile from "@/components/common/profile/Profile";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import { FeedbackCardData } from "@/@types/feedback";

const FeedbackCard = (feedbackCardData: FeedbackCardData) => {
  return (
    <S.FeedbackCardContainer>
      <S.FeedbackProfile>
        <Profile imgSrc={feedbackCardData.profile} />
        <S.FeedbackTitle>{feedbackCardData.username}</S.FeedbackTitle>
      </S.FeedbackProfile>
      <S.FeedbackScoreContainer>
        <S.FeedbackTitle>피드백 점수</S.FeedbackTitle>
        <EvaluationPointBar initialOptionId={feedbackCardData.evaluationPoint} readonly={true} />
      </S.FeedbackScoreContainer>
      <S.FeedbackKeywordContainer>
        <S.FeedbackTitle>피드백 키워드</S.FeedbackTitle>
        <S.FeedbackKeywordWrapper>
          {feedbackCardData.feedbackKeywords.map((keyword) => (
            <S.FeedbackKeyword>{keyword}</S.FeedbackKeyword>
          ))}
        </S.FeedbackKeywordWrapper>
      </S.FeedbackKeywordContainer>
      <S.FeedbackDetailContainer>
        <S.FeedbackTitle>세부 피드백</S.FeedbackTitle>
        <S.FeedbackDetail>
          {feedbackCardData.feedbackText.length ? feedbackCardData.feedbackText : "없음"}
        </S.FeedbackDetail>
      </S.FeedbackDetailContainer>
      <Button size="large">자세히 보기</Button>
    </S.FeedbackCardContainer>
  );
};

export default FeedbackCard;
