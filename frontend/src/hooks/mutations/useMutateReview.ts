import { useMutation, useQueryClient } from "@tanstack/react-query";
import useToast from "@/hooks/common/useToast";
import { ReviewReminderAlarm } from "@/@types/alarm";
import QUERY_KEYS from "@/apis/queryKeys";
import { postReviewComplete, postReviewUrge } from "@/apis/reviews.api";
import MESSAGES from "@/constants/message";

const useMutateReview = (roomId: number) => {
  const { showToast } = useToast();

  const queryClient = useQueryClient();

  const postReviewCompleteMutation = useMutation({
    mutationFn: ({ roomId, revieweeId }: { roomId: number; revieweeId: number }) =>
      postReviewComplete(roomId, revieweeId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.REVIEWEES, roomId] });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.ALARM_COUNT],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.ALARM_LIST],
      });
      showToast(MESSAGES.SUCCESS.POST_REVIEW_COMPLETE, "success");
    },
  });

  const postReviewUrgeMutation = useMutation({
    mutationFn: ({ roomId, reviewerId }: ReviewReminderAlarm) =>
      postReviewUrge({ roomId, reviewerId }),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.REVIEWERS, roomId],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.ALARM_COUNT],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.ALARM_LIST],
      });
      showToast(MESSAGES.SUCCESS.POST_REVIEW_URGE, "success");
    },
  });

  return { postReviewCompleteMutation, postReviewUrgeMutation };
};

export default useMutateReview;
