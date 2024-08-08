import { useState } from "react";
import {
  useFetchDeliveredFeedback,
  useFetchReceivedFeedback,
} from "@/hooks/queries/useFetchFeedback";
import { FeedbackCardData } from "@/@types/feedback";

type FeedbackType = "받은 피드백" | "쓴 피드백";

const groupFeedbacksByUser = (feedbacks: FeedbackCardData[]) => {
  return feedbacks.reduce(
    (acc, feedback) => {
      if (!acc[feedback.username]) acc[feedback.username] = [feedback];
      else acc[feedback.username].push(feedback);

      return acc;
    },
    {} as Record<string, FeedbackCardData[]>,
  );
};

const useSelectedFeedbackData = () => {
  const [selectedFeedbackType, setSelectedFeedbackType] = useState<FeedbackType>("받은 피드백");

  const { data: receivedFeedbacks } = useFetchReceivedFeedback(
    selectedFeedbackType === "받은 피드백",
  );
  const { data: deliveredFeedbacks } = useFetchDeliveredFeedback(
    selectedFeedbackType === "쓴 피드백",
  );

  const selectedFeedbackDatas =
    selectedFeedbackType === "받은 피드백" ? receivedFeedbacks : deliveredFeedbacks;

  const feedbacks =
    selectedFeedbackDatas?.length !== 0
      ? selectedFeedbackDatas?.map((feedback) => [
          ...feedback.socialFeedback,
          ...feedback.developFeedback,
        ])
      : [];

  const userFeedbacks = feedbacks?.map((feedback) => groupFeedbacksByUser(feedback));

  console.log(userFeedbacks);

  return { selectedFeedbackType, setSelectedFeedbackType, userFeedbacks };
};

export default useSelectedFeedbackData;
