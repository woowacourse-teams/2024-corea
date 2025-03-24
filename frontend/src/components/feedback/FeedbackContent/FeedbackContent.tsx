import EvaluationPointBar from "../evaluationPointBar/EvaluationPointBar";
import * as S from "./FeedbackContent.style";
import React from "react";
import { Textarea } from "@/components/common/textarea/Textarea";
import type { FeedbackCardData } from "@/@types/feedback";
import { theme } from "@/styles/theme";

interface FeedbackContentProps {
  feedbackCardData: FeedbackCardData;
  feedbackType: "develop" | "social";
}

const FeedbackContent = ({ feedbackCardData, feedbackType }: FeedbackContentProps) => {
  return (
    <S.FeedbackContentContainer $isWrited={feedbackCardData.isWrited}>
      <S.FeedbackScoreContainer>
        <S.FeedbackSubTitle>피드백 점수</S.FeedbackSubTitle>
        <EvaluationPointBar
          initialOptionId={feedbackCardData.evaluationPoint}
          readonly={true}
          color={feedbackType === "social" ? theme.COLOR.secondary : undefined}
          isTabFocusable={false}
        />
      </S.FeedbackScoreContainer>

      <S.FeedbackKeywordContainer>
        <S.FeedbackSubTitle>피드백 키워드</S.FeedbackSubTitle>
        <S.FeedbackKeywordWrapper>
          {feedbackCardData.feedbackKeywords.map((keyword) => (
            <S.FeedbackKeyword key={keyword}>{keyword}</S.FeedbackKeyword>
          ))}
        </S.FeedbackKeywordWrapper>
      </S.FeedbackKeywordContainer>

      <S.FeedbackDetailContainer>
        <S.FeedbackSubTitle>세부 피드백</S.FeedbackSubTitle>
        <Textarea
          rows={7}
          maxLength={2000}
          showCharCount={true}
          placeholder="미작성"
          value={feedbackCardData.feedbackText}
          disabled
        />
      </S.FeedbackDetailContainer>
    </S.FeedbackContentContainer>
  );
};

export default FeedbackContent;
