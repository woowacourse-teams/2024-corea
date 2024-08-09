import { useMutation } from "@tanstack/react-query";
import useMutateHandlers from "@/hooks/mutations/useMutateHandlers";
import { RevieweeFeedbackData, ReviewerFeedbackData } from "@/@types/feedback";
import {
  postRevieweeFeedback,
  postReviewerFeedback,
  putRevieweeFeedback,
  putReviewerFeedback,
} from "@/apis/feedback.api";
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

interface PostReviewerFeedbackMutationProps {
  roomId: number;
  feedbackData: Omit<ReviewerFeedbackData, "feedbackId">;
}

interface PutReviewerFeedbackMutationProps {
  roomId: number;
  feedbackId: number;
  feedbackData: ReviewerFeedbackData;
}

const useMutateFeedback = () => {
  const { handleMutateSuccess, handleMutateError } = useMutateHandlers();

  // 리뷰어 -> 리뷰이
  const postRevieweeFeedbackMutation = useMutation({
    mutationFn: ({ roomId, feedbackData }: PostRevieweeFeedbackMutationProps) =>
      postRevieweeFeedback(roomId, feedbackData),
    onSuccess: (_, variables) =>
      handleMutateSuccess([
        QUERY_KEYS.REVIEWEES,
        QUERY_KEYS.REVIEWEE_FEEDBACK,
        variables.roomId.toString(),
      ]),
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  const putRevieweeFeedbackMutation = useMutation({
    mutationFn: ({ roomId, feedbackId, feedbackData }: PutRevieweeFeedbackMutationProps) =>
      putRevieweeFeedback(roomId, feedbackId, feedbackData),
    onSuccess: (_, variables) =>
      handleMutateSuccess([
        QUERY_KEYS.REVIEWEES,
        QUERY_KEYS.REVIEWEE_FEEDBACK,
        variables.roomId.toString(),
      ]),
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  // 리뷰이 -> 리뷰어
  const postReviewerFeedbackMutation = useMutation({
    mutationFn: ({ roomId, feedbackData }: PostReviewerFeedbackMutationProps) =>
      postReviewerFeedback(roomId, feedbackData),
    onSuccess: (_, variables) =>
      handleMutateSuccess([
        QUERY_KEYS.REVIEWERS,
        QUERY_KEYS.REVIEWER_FEEDBACK,
        variables.roomId.toString(),
      ]),
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  const putReviewerFeedbackMutation = useMutation({
    mutationFn: ({ roomId, feedbackId, feedbackData }: PutReviewerFeedbackMutationProps) =>
      putReviewerFeedback(roomId, feedbackId, feedbackData),
    onSuccess: (_, variables) =>
      handleMutateSuccess([
        QUERY_KEYS.REVIEWERS,
        QUERY_KEYS.REVIEWER_FEEDBACK,
        variables.roomId.toString(),
      ]),
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  return {
    postRevieweeFeedbackMutation,
    putRevieweeFeedbackMutation,
    postReviewerFeedbackMutation,
    putReviewerFeedbackMutation,
  };
};

export default useMutateFeedback;
