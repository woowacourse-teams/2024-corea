import * as S from "./FeedbackCard.style";
import Button from "@/components/common/button/Button";
import Profile from "@/components/common/profile/Profile";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import { FeedbackCardData } from "@/@types/feedback";
import { theme } from "@/styles/theme";

interface FeedbackCardProps {
  feedbackCardData: FeedbackCardData;
  feedbackType: "develop" | "social";
}

const FeedbackCard = ({ feedbackCardData, feedbackType }: FeedbackCardProps) => {
  const feedbackTypeInfo =
    feedbackType === "develop" ? "개발역량 피드백" : "소프트스킬 역량 피드백";

  return (
    <S.FeedbackCardContainer $isTypeDevelop={feedbackType === "develop"}>
      <S.FeedbackHeader>
        <S.FeedbackProfile>
          <Profile imgSrc={feedbackCardData.profile} />
          <S.FeedbackTitle>{feedbackCardData.username}</S.FeedbackTitle>
        </S.FeedbackProfile>
        <S.FeedbackType $isTypeDevelop={feedbackType === "develop"}>
          {feedbackTypeInfo}
        </S.FeedbackType>
      </S.FeedbackHeader>
      <S.FeedbackScoreContainer>
        <S.FeedbackTitle>피드백 점수</S.FeedbackTitle>
        <EvaluationPointBar
          initialOptionId={feedbackCardData.evaluationPoint}
          readonly={true}
          color={feedbackType === "social" ? theme.COLOR.secondary : undefined}
        />
      </S.FeedbackScoreContainer>
      <S.FeedbackKeywordContainer>
        <S.FeedbackTitle>피드백 키워드</S.FeedbackTitle>
        <S.FeedbackKeywordWrapper>
          {feedbackCardData.feedbackKeywords.map((keyword) => (
            <S.FeedbackKeyword key={keyword}>{keyword}</S.FeedbackKeyword>
          ))}
        </S.FeedbackKeywordWrapper>
      </S.FeedbackKeywordContainer>
      <S.FeedbackDetailContainer>
        <S.FeedbackTitle>세부 피드백</S.FeedbackTitle>
        <S.FeedbackDetail>
          {feedbackCardData.feedbackText.length ? feedbackCardData.feedbackText : "없음"}
        </S.FeedbackDetail>
      </S.FeedbackDetailContainer>
      <Button
        size="large"
        style={{
          background: feedbackType === "develop" ? theme.COLOR.primary2 : theme.COLOR.secondary,
        }}
      >
        자세히 보기
      </Button>
    </S.FeedbackCardContainer>
  );
};

export default FeedbackCard;
