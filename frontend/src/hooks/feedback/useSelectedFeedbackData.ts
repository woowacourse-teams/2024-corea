import { useState } from "react";
import {
  useFetchDeliveredFeedback,
  useFetchReceivedFeedback,
} from "@/hooks/queries/useFetchFeedback";

export type FeedbackType = "받은 피드백" | "쓴 피드백";

const useSelectedFeedbackData = (initialFeedbackType: FeedbackType = "받은 피드백") => {
  const [selectedFeedbackType, setSelectedFeedbackType] = useState(() => {
    return (sessionStorage.getItem("selectedFeedbackType") as FeedbackType) || initialFeedbackType;
  });

  const handleSelectedFeedbackType = (feedbackType: FeedbackType) => {
    setSelectedFeedbackType(feedbackType);
    sessionStorage.setItem("selectedFeedbackType", feedbackType);
  };

  const { data: receivedFeedbacks } = useFetchReceivedFeedback(
    selectedFeedbackType === "받은 피드백",
  );
  const { data: deliveredFeedbacks } = useFetchDeliveredFeedback(
    selectedFeedbackType === "쓴 피드백",
  );

  const selectedFeedbackData =
    selectedFeedbackType === "받은 피드백" ? receivedFeedbacks : deliveredFeedbacks;

  return { selectedFeedbackType, handleSelectedFeedbackType, selectedFeedbackData };
};

export default useSelectedFeedbackData;
