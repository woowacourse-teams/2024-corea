import useMutateHandlers from "./useMutateHandlers";
import { useMutation } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { postReviewComplete } from "@/apis/reviews.api";

const useMutateReviewComplete = () => {
  const { handleMutateSuccess, handleMutateError } = useMutateHandlers();

  const postReviewCompleteMutation = useMutation({
    mutationFn: ({ roomId, revieweeId }: { roomId: number; revieweeId: number }) =>
      postReviewComplete(roomId, revieweeId),
    onSuccess: () => handleMutateSuccess([QUERY_KEYS.REVIEWEES]),
    onError: (error) => handleMutateError(error),
  });

  return { postReviewCompleteMutation };
};

export default useMutateReviewComplete;
