import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import useToast from "@/hooks/common/useToast";
import QUERY_KEYS from "@/apis/queryKeys";
import { postReviewComplete } from "@/apis/reviews.api";
import MESSAGES from "@/constants/message";

const useMutateReviewComplete = (roomId: number) => {
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

  return { postReviewCompleteMutation };
};

export default useMutateReviewComplete;
