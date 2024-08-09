import * as S from "./FeedbackPage.style";
import React from "react";
import useSelectedFeedbackData from "@/hooks/feedback/useSelectedFeedbackData";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import FeedbackCardList from "@/components/feedback/feedbackCardList/FeedbackCardList";

const options = ["쓴 피드백", "받은 피드백"] as const;

const FeedbackPage = () => {
  const { selectedFeedbackType, setSelectedFeedbackType, userFeedbacks, selectedFeedbackDatas } =
    useSelectedFeedbackData();

  if (!userFeedbacks || !selectedFeedbackDatas) return;

  return (
    <>
      <OptionSelect
        selected={selectedFeedbackType}
        options={options}
        handleSelectedOption={(option) => setSelectedFeedbackType(option)}
      />
      <S.FeedbackCardContainer>
        {userFeedbacks.map((userFeedback, userIdx) => (
          <React.Fragment key={userIdx}>
            <S.FeedbackMissionInfo>{selectedFeedbackDatas[userIdx].title}</S.FeedbackMissionInfo>
            <FeedbackCardList userFeedback={userFeedback} />
          </React.Fragment>
        ))}
      </S.FeedbackCardContainer>
    </>
  );
};

export default FeedbackPage;
