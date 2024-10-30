import { FeedbackType } from "@/hooks/feedback/useSelectedFeedbackData";
import Profile from "@/components/common/profile/Profile";
import { Textarea } from "@/components/common/textarea/Textarea";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import * as S from "@/components/feedback/feedbackCard/FeedbackCard.style";
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
  const getFeedbackTarget = (feedbackType: "develop" | "social") => {
    if (selectedFeedbackType === "받은 피드백") {
      return feedbackType === "develop" ? "FROM. 나의 리뷰어" : "FROM. 나의 리뷰이";
    }
    return feedbackType === "develop" ? "TO. 나의 리뷰이" : "TO. 나의 리뷰어";
  };

  return (
    <>
      <S.ScreenReader>미션의 상세 피드백 내용입니다.</S.ScreenReader>
      <S.FeedbackCardContainer $isTypeDevelop={feedbackType === "develop"}>
        <S.FeedbackHeader>
          <HoverStyledLink to={`/profile/${feedbackCardData.username}`} tabIndex={-1}>
            <S.FeedbackProfile>
              <Profile imgSrc={feedbackCardData.profile} tabIndex={-1} />
              <S.FeedbackTitle>{feedbackCardData.username}</S.FeedbackTitle>
            </S.FeedbackProfile>
          </HoverStyledLink>
          <S.FeedbackType $isTypeDevelop={feedbackType === "develop"}>
            {feedbackType === "develop" ? (
              <>
                개발 역량 피드백
                <p>{getFeedbackTarget(feedbackType)}</p>
              </>
            ) : (
              <>
                소프트스킬 역량 피드백
                <p>{getFeedbackTarget(feedbackType)}</p>
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
            isTabFocusable={false}
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
          <Textarea
            rows={10}
            maxLength={2000}
            showCharCount={true}
            value={feedbackCardData.feedbackText.length ? feedbackCardData.feedbackText : "없음"}
            readOnly
          />
        </S.FeedbackDetailContainer>
      </S.FeedbackCardContainer>
    </>
  );
};

export default FeedbackCard;
