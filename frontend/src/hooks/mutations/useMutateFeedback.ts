import { useMutation, useQueryClient } from "@tanstack/react-query";
import useToast from "@/hooks/common/useToast";
import useMutateHandlers from "@/hooks/mutations/useMutateHandlers";
import { RevieweeFeedbackData, ReviewerFeedbackData } from "@/@types/feedback";
import {
  postRevieweeFeedback,
  postReviewerFeedback,
  putRevieweeFeedback,
  putReviewerFeedback,
} from "@/apis/feedback.api";
import QUERY_KEYS from "@/apis/queryKeys";
import MESSAGES from "@/constants/message";

interface PostRevieweeFeedbackMutationProps {
  feedbackData: Omit<RevieweeFeedbackData, "feedbackId">;
}

interface PutRevieweeFeedbackMutationProps {
  feedbackId: number;
  feedbackData: RevieweeFeedbackData;
}

interface PostReviewerFeedbackMutationProps {
  feedbackData: Omit<ReviewerFeedbackData, "feedbackId">;
}

interface PutReviewerFeedbackMutationProps {
  feedbackId: number;
  feedbackData: ReviewerFeedbackData;
}

const useMutateFeedback = (roomId: number) => {
  const { handleMutateError } = useMutateHandlers();
  const { showToast } = useToast();
  const queryClient = useQueryClient();

  // 리뷰어 -> 리뷰이
  const postRevieweeFeedbackMutation = useMutation({
    mutationFn: ({ feedbackData }: PostRevieweeFeedbackMutationProps) =>
      postRevieweeFeedback(roomId, feedbackData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.REVIEWEES, roomId] });
      showToast(MESSAGES.SUCCESS.POST_REVIEW_FEEDBACK, "success");
    },
    onError: (error) => handleMutateError(error),
  });

  const putRevieweeFeedbackMutation = useMutation({
    mutationFn: ({ feedbackId, feedbackData }: PutRevieweeFeedbackMutationProps) =>
      putRevieweeFeedback(roomId, feedbackId, feedbackData),
    onSuccess: () => {
      showToast(MESSAGES.SUCCESS.PUT_REVIEW_FEEDBACK, "success");
    },
    onError: (error) => handleMutateError(error),
  });

  // 리뷰이 -> 리뷰어
  const postReviewerFeedbackMutation = useMutation({
    mutationFn: ({ feedbackData }: PostReviewerFeedbackMutationProps) =>
      postReviewerFeedback(roomId, feedbackData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.REVIEWERS, roomId] });
      showToast(MESSAGES.SUCCESS.POST_REVIEW_FEEDBACK, "success");
    },
    onError: (error) => handleMutateError(error),
  });

  const putReviewerFeedbackMutation = useMutation({
    mutationFn: ({ feedbackId, feedbackData }: PutReviewerFeedbackMutationProps) =>
      putReviewerFeedback(roomId, feedbackId, feedbackData),
    onSuccess: () => {
      showToast(MESSAGES.SUCCESS.PUT_REVIEW_FEEDBACK);
    },
    onError: (error) => handleMutateError(error),
  });

  return {
    postRevieweeFeedbackMutation,
    putRevieweeFeedbackMutation,
    postReviewerFeedbackMutation,
    putReviewerFeedbackMutation,
  };
};

export default useMutateFeedback;
