import * as S from "./FeedbackCard.style";
import React from "react";
import Button from "@/components/common/button/Button";
import EvaluationPointBar from "@/components/feedback/evaluationPointBar/EvaluationPointBar";
import { FeedbackCardData } from "@/@types/feedback";

const FeedbackCard = (feedbackCardData: FeedbackCardData) => {
  return (
    <S.FeedbackCardContainer>
      <S.FeedbackTitle>{feedbackCardData.nickname}</S.FeedbackTitle>
      <EvaluationPointBar initialOptionId={feedbackCardData.evaluationPoint} readonly={true} />
      <div>
        <S.FeedbackTitle>피드백 키워드</S.FeedbackTitle>
        <S.FeedbackKeywordContainer>
          {feedbackCardData.feedbackKeywords.map((keyword) => (
            <S.FeedbackKeyword>{keyword}</S.FeedbackKeyword>
          ))}
        </S.FeedbackKeywordContainer>
      </div>
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
