import * as S from "./FeedbackCard.style";
import { FeedbackType } from "@/hooks/feedback/useSelectedFeedbackData";
import Profile from "@/components/common/profile/Profile";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import { FeedbackCardData } from "@/@types/feedback";
import { HoverStyledLink } from "@/styles/common";
import { theme } from "@/styles/theme";

interface FeedbackCardProps {
  selectedFeedbackType: FeedbackType;
  feedbackCardData: FeedbackCardData;
  feedbackType: "develop" | "social";
}

const FeedbackCard = ({
  selectedFeedbackType,
  feedbackCardData,
  feedbackType,
}: FeedbackCardProps) => {
  const feedbackTarget = selectedFeedbackType === "받은 피드백" ? "FROM" : "TO";

  return (
    <S.FeedbackCardContainer $isTypeDevelop={feedbackType === "develop"}>
      <S.FeedbackHeader>
        <HoverStyledLink to={`/profile/${feedbackCardData.username}`}>
          <S.FeedbackProfile>
            <Profile imgSrc={feedbackCardData.profile} />
            <S.FeedbackTitle>{feedbackCardData.username}</S.FeedbackTitle>
          </S.FeedbackProfile>
        </HoverStyledLink>
        <S.FeedbackType $isTypeDevelop={feedbackType === "develop"}>
          {feedbackType === "develop" ? (
            <>
              개발 역량 피드백
              <p>{feedbackTarget}. 나의 리뷰어</p>
            </>
          ) : (
            <>
              소프트스킬 역량 피드백
              <p>{feedbackTarget}. 나의 리뷰이</p>
            </>
          )}
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
    </S.FeedbackCardContainer>
  );
};

export default FeedbackCard;
