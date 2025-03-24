import * as S from "./FeedbackCardHeader.style";
import React from "react";
import Profile from "@/components/common/profile/Profile";
import type { FeedbackCardData, FeedbackType } from "@/@types/feedback";
import { HoverStyledLink } from "@/styles/common";

interface FeedbackCardHeaderProps {
  selectedFeedbackType: FeedbackType;
  feedbackCardData: FeedbackCardData;
  feedbackType: "develop" | "social";
}

const FeedbackCardHeader = ({
  selectedFeedbackType,
  feedbackCardData,
  feedbackType,
}: FeedbackCardHeaderProps) => {
  const getFeedbackTarget = (feedbackType: "develop" | "social") => {
    if (selectedFeedbackType === "받은 피드백") {
      return feedbackType === "develop" ? "FROM. 나의 리뷰어" : "FROM. 나의 리뷰이";
    }
    return feedbackType === "develop" ? "TO. 나의 리뷰이" : "TO. 나의 리뷰어";
  };

  return (
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
  );
};

export default FeedbackCardHeader;
