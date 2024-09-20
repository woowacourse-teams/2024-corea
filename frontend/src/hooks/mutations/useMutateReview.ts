import useMutateHandlers from "./useMutateHandlers";
import { useMutation } from "@tanstack/react-query";
import { postReviewComplete } from "@/apis/reviews.api";

const useMutateReviewComplete = () => {
  const { handleMutateError } = useMutateHandlers();

  const postReviewCompleteMutation = useMutation({
    mutationFn: ({ roomId, revieweeId }: { roomId: number; revieweeId: number }) =>
      postReviewComplete(roomId, revieweeId),
    onError: (error) => handleMutateError(error),
  });

  return { postReviewCompleteMutation };
};

export default useMutateReviewComplete;
