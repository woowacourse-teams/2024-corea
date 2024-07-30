import useToast from "../common/useToast";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { postParticipateIn } from "@/apis/rooms.api";

interface useMutateParticipateInResult {
  handleParticipateIn: (roomId: number) => void;
}

const useMutateParticipateIn = (): useMutateParticipateInResult => {
  const queryClient = useQueryClient();
  const { openToast } = useToast();

  const onSuccess = () => {
    queryClient.invalidateQueries({
      queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST, QUERY_KEYS.OPENED_ROOM_LIST],
    });
  };

  const onError = (error: Error) => {
    openToast(error.message);
  };

  const participateIn = useMutation({
    mutationFn: (roomId: number) => postParticipateIn(roomId),
    onSuccess,
    onError: (error) => onError(error),
    networkMode: "always",
  });

  const handleParticipateIn = (productId: number) => {
    participateIn.mutate(productId);
  };

  return { handleParticipateIn };
};

export default useMutateParticipateIn;
