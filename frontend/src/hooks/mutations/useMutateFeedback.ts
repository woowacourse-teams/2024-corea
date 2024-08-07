import { useMutation } from "@tanstack/react-query";
import useMutateHandlers from "@/hooks/mutations/useMutateHandlers";
import { RevieweeFeedbackData } from "@/@types/feedback";
import { postRevieweeFeedback, putRevieweeFeedback } from "@/apis/feedback.api";
import QUERY_KEYS from "@/apis/queryKeys";

interface PostRevieweeFeedbackMutationProps {
  roomId: number;
  feedbackData: Omit<RevieweeFeedbackData, "feedbackId">;
}

interface PutRevieweeFeedbackMutationProps {
  roomId: number;
  feedbackId: number;
  feedbackData: RevieweeFeedbackData;
}

const useMutateFeedback = () => {
  const { handleMutateSuccess, handleMutateError } = useMutateHandlers();

  const postRevieweeFeedbackMutation = useMutation({
    mutationFn: ({ roomId, feedbackData }: PostRevieweeFeedbackMutationProps) =>
      postRevieweeFeedback(roomId, feedbackData),
    onSuccess: (_, variables) =>
      handleMutateSuccess([QUERY_KEYS.REVIEWEE_FEEDBACK, variables.roomId.toString()]),
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  const putRevieweeFeedbackMutation = useMutation({
    mutationFn: ({ roomId, feedbackId, feedbackData }: PutRevieweeFeedbackMutationProps) =>
      putRevieweeFeedback(roomId, feedbackId, feedbackData),
    onSuccess: (_, variables) =>
      handleMutateSuccess([QUERY_KEYS.REVIEWEE_FEEDBACK, variables.roomId.toString()]),
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  return { postRevieweeFeedbackMutation, putRevieweeFeedbackMutation };
};

export default useMutateFeedback;
