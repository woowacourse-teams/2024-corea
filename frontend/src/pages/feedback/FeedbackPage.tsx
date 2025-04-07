import useSelectedFeedbackData from "@/hooks/feedback/useSelectedFeedbackData";
import {
  useFetchDeliveredFeedback,
  useFetchReceivedFeedback,
} from "@/hooks/queries/useFetchFeedback";
import OptionSelect from "@/components/common/optionSelect/OptionSelect";
import FeedbackCardList from "@/components/feedback/feedbackCardList/FeedbackCardList";

const options = ["받은 피드백", "쓴 피드백"] as const;

const FeedbackPage = () => {
  const {
    selectedFeedbackType,
    handleSelectedFeedbackType,
    selectedFeedback,
    handleSelectedFeedback,
    handleDeselectedFeedback,
  } = useSelectedFeedbackData();

  const { data: receivedFeedbacks } = useFetchReceivedFeedback();
  const { data: deliveredFeedbacks } = useFetchDeliveredFeedback();

  const selectedFeedbackData =
    selectedFeedbackType === "받은 피드백" ? receivedFeedbacks : deliveredFeedbacks;

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
        handleDeselectedFeedback={handleDeselectedFeedback}
      />
    </>
  );
};

export default FeedbackPage;
