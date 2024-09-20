import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { postParticipateIn } from "@/apis/rooms.api";

const useMutateParticipateIn = () => {
  const queryClient = useQueryClient();
  const { handleMutateError } = useMutateHandlers();

  const postParticipateInMutation = useMutation({
    mutationFn: (roomId: number) => postParticipateIn(roomId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST] });
    },
    onError: (error) => handleMutateError(error),
  });

  return { postParticipateInMutation };
};

export default useMutateParticipateIn;
