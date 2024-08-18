import useSelectedFeedbackData from "@/hooks/feedback/useSelectedFeedbackData";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import FeedbackCardList from "@/components/feedback/feedbackCardList/FeedbackCardList";

const options = ["받은 피드백", "쓴 피드백"] as const;

const FeedbackPage = () => {
  const { selectedFeedbackType, setSelectedFeedbackType, selectedFeedbackData } =
    useSelectedFeedbackData();

  if (!selectedFeedbackData) return;

  return (
    <>
      <OptionSelect
        selected={selectedFeedbackType}
        options={options}
        handleSelectedOption={(option) => setSelectedFeedbackType(option)}
      />
      <FeedbackCardList feedbackData={selectedFeedbackData} />
    </>
  );
};

export default FeedbackPage;
