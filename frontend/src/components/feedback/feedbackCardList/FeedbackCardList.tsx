import * as S from "./FeedbackCardList.style";
import React from "react";
import FeedbackCard from "@/components/feedback/feedbackCard/FeedbackCard";
import { FeedbackCardData } from "@/@types/feedback";

interface FeedbackCardListProps {
  userFeedback: Record<string, FeedbackCardData[]>;
}

const FeedbackCardList = ({ userFeedback }: FeedbackCardListProps) => {
  return (
    <S.FeedbackCardListContainer>
      <S.XScrollWrapper>
        {Object.entries(userFeedback).map(([username, feedbackDatas]) => (
          <S.FeedbackCardListWrapper>
            {feedbackDatas.map((feedbackData, idx) => (
              <FeedbackCard key={idx} {...feedbackData} />
            ))}
          </S.FeedbackCardListWrapper>
        ))}
      </S.XScrollWrapper>
    </S.FeedbackCardListContainer>
  );
};

export default FeedbackCardList;
