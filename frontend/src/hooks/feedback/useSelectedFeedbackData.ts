import { useState } from "react";
import {
  useFetchDeliveredFeedback,
  useFetchReceivedFeedback,
} from "@/hooks/queries/useFetchFeedback";

export type FeedbackType = "쓴 피드백" | "받은 피드백";

const useSelectedFeedbackData = () => {
  const [selectedFeedbackType, setSelectedFeedbackType] = useState<FeedbackType>("받은 피드백");

  const { data: receivedFeedbacks } = useFetchReceivedFeedback(
    selectedFeedbackType === "받은 피드백",
  );
  const { data: deliveredFeedbacks } = useFetchDeliveredFeedback(
    selectedFeedbackType === "쓴 피드백",
  );

  const selectedFeedbackData =
    selectedFeedbackType === "받은 피드백" ? receivedFeedbacks : deliveredFeedbacks;

  return { selectedFeedbackType, setSelectedFeedbackType, selectedFeedbackData };
};

export default useSelectedFeedbackData;
