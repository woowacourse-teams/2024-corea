import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { postReviewComplete } from "@/apis/reviews.api";

const useMutateReviewComplete = (roomId: number) => {
  const { handleMutateError } = useMutateHandlers();
  const queryClient = useQueryClient();

  const postReviewCompleteMutation = useMutation({
    mutationFn: ({ roomId, revieweeId }: { roomId: number; revieweeId: number }) =>
      postReviewComplete(roomId, revieweeId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.REVIEWEES, roomId] });
    },
    onError: (error) => handleMutateError(error),
  });

  return { postReviewCompleteMutation };
};

export default useMutateReviewComplete;
