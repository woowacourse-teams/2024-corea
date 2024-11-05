import useSelectedFeedbackData from "@/hooks/feedback/useSelectedFeedbackData";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import FeedbackCardList from "@/components/feedback/feedbackCardList/FeedbackCardList";

const options = ["받은 피드백", "쓴 피드백"] as const;

const FeedbackPage = () => {
  const {
    selectedFeedbackType,
    handleSelectedFeedbackType,
    selectedFeedback,
    handleSelectedFeedback,
    selectedFeedbackData,
  } = useSelectedFeedbackData();

  if (!selectedFeedbackData) return;

  return (
    <>
      <OptionSelect
        selected={selectedFeedbackType}
        options={options}
        handleSelectedOption={handleSelectedFeedbackType}
      />
      <FeedbackCardList
        selectedFeedbackType={selectedFeedbackType}
        feedbackData={selectedFeedbackData}
        selectedFeedback={selectedFeedback}
        handleSelectedFeedback={handleSelectedFeedback}
      />
    </>
  );
};

export default FeedbackPage;
