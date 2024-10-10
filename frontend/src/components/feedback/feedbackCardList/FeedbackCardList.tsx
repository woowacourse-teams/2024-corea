import React, { useEffect, useState } from "react";
import { FeedbackType } from "@/hooks/feedback/useSelectedFeedbackData";
import Carousel from "@/components/common/carousel/Carousel";
import Label from "@/components/common/label/Label";
import FeedbackCard from "@/components/feedback/feedbackCard/FeedbackCard";
import * as S from "@/components/feedback/feedbackCardList/FeedbackCardList.style";
import { FeedbackCardDataList } from "@/@types/feedback";
import { defaultCharacter } from "@/assets";
import { theme } from "@/styles/theme";

interface FeedbackCardListProps {
  selectedFeedbackType: FeedbackType;
  feedbackData: FeedbackCardDataList[];
}

const FeedbackCardList = ({ selectedFeedbackType, feedbackData }: FeedbackCardListProps) => {
  const [selectedFeedback, setSelectedFeedback] = useState<number>();

  const handleSelectedFeedback = (roomId: number) => {
    if (selectedFeedback === roomId) {
      setSelectedFeedback(undefined);
      return;
    }

    setSelectedFeedback(roomId);
  };

  useEffect(
    function resetSelectedFeedback() {
      setSelectedFeedback(undefined);
    },
    [feedbackData],
  );

  if (feedbackData.length === 0) {
    return (
      <S.EmptyContainer>
        <S.Character src={defaultCharacter} alt="기본 캐릭터" />
        <p>{selectedFeedbackType}이 없습니다.</p>
      </S.EmptyContainer>
    );
  }

  return (
    <S.FeedbackCardContainer>
      {feedbackData.map((feedback) => (
        <React.Fragment key={feedback.roomId}>
          <S.FeedbackMissionWrapper
            $isSelected={selectedFeedback === feedback.roomId}
            onClick={() => handleSelectedFeedback(feedback.roomId)}
          >
            <S.FeedbackMissionTitle>
              <S.FeedbackMissionInfo>{feedback.title}</S.FeedbackMissionInfo>
              <S.FeedbackKeywordContainer>
                {feedback.roomKeywords.map((keyword) => (
                  <Label
                    key={keyword}
                    type="KEYWORD"
                    text={keyword}
                    size="semiSmall"
                    backgroundColor={theme.COLOR.white}
                  />
                ))}
              </S.FeedbackKeywordContainer>
            </S.FeedbackMissionTitle>
            <S.FeedbackMissionPrompt $isSelected={selectedFeedback === feedback.roomId}>
              피드백을 보려면 클릭해주세요
            </S.FeedbackMissionPrompt>
          </S.FeedbackMissionWrapper>
          <S.FeedbackInfoWrapper $isVisible={feedback.roomId === selectedFeedback}>
            {feedback.roomId === selectedFeedback && (
              <Carousel>
                {feedback.developFeedback.map((developFeedback) => (
                  <FeedbackCard
                    key={developFeedback.feedbackId}
                    selectedFeedbackType={selectedFeedbackType}
                    feedbackCardData={developFeedback}
                    feedbackType="develop"
                  />
                ))}
                {feedback.socialFeedback.map((socialFeedback) => (
                  <FeedbackCard
                    key={socialFeedback.feedbackId}
                    selectedFeedbackType={selectedFeedbackType}
                    feedbackCardData={socialFeedback}
                    feedbackType="social"
                  />
                ))}
              </Carousel>
            )}
          </S.FeedbackInfoWrapper>
        </React.Fragment>
      ))}
    </S.FeedbackCardContainer>
  );
};

export default FeedbackCardList;
