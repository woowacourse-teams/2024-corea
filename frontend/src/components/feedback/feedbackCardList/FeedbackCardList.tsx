import React from "react";
import { FeedbackType } from "@/hooks/feedback/useSelectedFeedbackData";
import Carousel from "@/components/common/carousel/Carousel";
import Label from "@/components/common/label/Label";
import FeedbackCard from "@/components/feedback/feedbackCard/FeedbackCard";
import * as S from "@/components/feedback/feedbackCardList/FeedbackCardList.style";
import { FeedbackCardDataList } from "@/@types/feedback";
import { defaultCharacter } from "@/assets";
import { HoverStyledLink } from "@/styles/common";
import { theme } from "@/styles/theme";

interface FeedbackCardListProps {
  selectedFeedbackType: FeedbackType;
  feedbackData: FeedbackCardDataList[];
  selectedFeedback: number | undefined;
  handleSelectedFeedback: (roomId: number) => void;
}

const FeedbackCardList = ({
  selectedFeedbackType,
  feedbackData,
  selectedFeedback,
  handleSelectedFeedback,
}: FeedbackCardListProps) => {
  if (feedbackData.length === 0) {
    return (
      <S.EmptyContainer>
        <S.Character src={defaultCharacter} alt="기본 캐릭터" />
        <p>{selectedFeedbackType}이 없습니다.</p>
      </S.EmptyContainer>
    );
  }

  return (
    <>
      <S.ScreenReader>
        {selectedFeedbackType} 리스트에 {feedbackData.length}개의 미션이 있습니다.
      </S.ScreenReader>

      <S.FeedbackCardContainer>
        {feedbackData.map((feedback, index) => (
          <React.Fragment key={feedback.roomId}>
            <S.FeedbackMissionWrapper
              $isSelected={selectedFeedback === feedback.roomId}
              onClick={() => handleSelectedFeedback(feedback.roomId)}
              role="listitem"
              aria-expanded={selectedFeedback === feedback.roomId}
              tabIndex={0}
              onKeyDown={(e) => {
                if (e.key === "Enter" || e.key === " ") {
                  e.preventDefault();
                  handleSelectedFeedback(feedback.roomId);
                }
              }}
              aria-label={`${feedbackData.length}개의 미션 중 ${index + 1}번째 미션입니다.`}
            >
              <S.FeedbackMissionTitle>
                <S.FeedbackMissionInfo>
                  <S.FeedbackTitle>{feedback.title}</S.FeedbackTitle>
                  <S.FeedbackCount
                    aria-label={`총 ${feedback.developFeedback.length + feedback.socialFeedback.length}개의 피드백`}
                  >
                    ({feedback.developFeedback.length + feedback.socialFeedback.length})
                  </S.FeedbackCount>
                </S.FeedbackMissionInfo>
                <S.FeedbackKeywordContainer role="group" aria-label="피드백 키워드">
                  {feedback.roomKeywords.filter((keyword) => keyword.trim() !== "").length > 0 ? (
                    feedback.roomKeywords.map(
                      (keyword) =>
                        keyword.trim() !== "" && (
                          <Label
                            key={keyword}
                            type="KEYWORD"
                            text={keyword}
                            backgroundColor={theme.COLOR.white}
                          />
                        ),
                    )
                  ) : (
                    <S.NoKeywordText role="note" aria-label="지정된 키워드가 없습니다">
                      지정된 키워드 없음
                    </S.NoKeywordText>
                  )}
                </S.FeedbackKeywordContainer>
              </S.FeedbackMissionTitle>
              <S.FeedbackMissionPrompt
                $isSelected={selectedFeedback === feedback.roomId}
                aria-hidden={selectedFeedback === feedback.roomId}
              >
                피드백을 보려면 클릭해주세요
              </S.FeedbackMissionPrompt>
              {selectedFeedback === feedback.roomId && (
                <HoverStyledLink to={`/rooms/${feedback.roomId}`}>
                  상세페이지로 이동하기
                </HoverStyledLink>
              )}
            </S.FeedbackMissionWrapper>

            <S.FeedbackInfoWrapper
              $isVisible={feedback.roomId === selectedFeedback}
              aria-hidden={feedback.roomId !== selectedFeedback}
              role="region"
              aria-label={`${feedback.title}의 상세 피드백`}
            >
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
    </>
  );
};

export default FeedbackCardList;
