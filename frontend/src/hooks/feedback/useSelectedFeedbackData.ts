import { useState } from "react";
import {
  useFetchDeliveredFeedback,
  useFetchReceivedFeedback,
} from "@/hooks/queries/useFetchFeedback";
import { FeedbackType } from "@/@types/feedback";

const useSelectedFeedbackData = (initialFeedbackType: FeedbackType = "받은 피드백") => {
  const [selectedFeedbackType, setSelectedFeedbackType] = useState<FeedbackType>(() => {
    return (sessionStorage.getItem("selectedFeedbackType") as FeedbackType) || initialFeedbackType;
  });

  const [selectedFeedback, setSelectedFeedback] = useState<number | undefined>(() => {
    const savedFeedback = sessionStorage.getItem("selectedFeedback");
    return savedFeedback ? parseInt(savedFeedback, 10) : undefined;
  });

  const handleSelectedFeedbackType = (feedbackType: FeedbackType) => {
    setSelectedFeedbackType(feedbackType);
    sessionStorage.setItem("selectedFeedbackType", feedbackType);
    setSelectedFeedback(undefined);
    sessionStorage.removeItem("selectedFeedback");
  };

  const handleSelectedFeedback = (roomId: number) => {
    setSelectedFeedback(roomId);
    sessionStorage.setItem("selectedFeedback", roomId.toString());
  };

  const handleDeselectedFeedback = () => {
    setSelectedFeedback(undefined);
    sessionStorage.removeItem("selectedFeedback");
  };

  const { data: receivedFeedbacks } = useFetchReceivedFeedback(
    selectedFeedbackType === "받은 피드백",
  );
  const { data: deliveredFeedbacks } = useFetchDeliveredFeedback(
    selectedFeedbackType === "쓴 피드백",
  );

  const selectedFeedbackData =
    selectedFeedbackType === "받은 피드백" ? receivedFeedbacks : deliveredFeedbacks;

  return {
    selectedFeedbackType,
    handleSelectedFeedbackType,
    selectedFeedback,
    handleSelectedFeedback,
    handleDeselectedFeedback,
    selectedFeedbackData,
  };
};

export default useSelectedFeedbackData;
