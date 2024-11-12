import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import useToast from "@/hooks/common/useToast";
import { RevieweReminderAlarm } from "@/@types/alaram";
import QUERY_KEYS from "@/apis/queryKeys";
import { postReviewComplete, postReviewUrge } from "@/apis/reviews.api";
import MESSAGES from "@/constants/message";

const useMutateReview = (roomId: number) => {
  const { handleMutateError } = useMutateHandlers();
  const { openToast } = useToast("success");

  const queryClient = useQueryClient();

  const postReviewCompleteMutation = useMutation({
    mutationFn: ({ roomId, revieweeId }: { roomId: number; revieweeId: number }) =>
      postReviewComplete(roomId, revieweeId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.REVIEWEES, roomId] });
      openToast(MESSAGES.SUCCESS.POST_REVIEW_COMPLETE);
    },
    onError: (error) => handleMutateError(error),
  });

  const postReviewUrgeMutation = useMutation({
    mutationFn: ({ roomId, reviewerId }: RevieweReminderAlarm) =>
      postReviewUrge({ roomId, reviewerId }),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.REVIEWERS, roomId, QUERY_KEYS.ALARM_COUNT, QUERY_KEYS.ALARM_LIST],
      });
      openToast(MESSAGES.SUCCESS.POST_REVIEW_URGE);
    },
    onError: (error) => handleMutateError(error),
  });

  return { postReviewCompleteMutation, postReviewUrgeMutation };
};

export default useMutateReview;
