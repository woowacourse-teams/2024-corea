import useMutateHandlers from "./useMutateHandlers";
import { useMutation } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { postParticipateIn } from "@/apis/rooms.api";

const useMutateParticipateIn = () => {
  const { handleMutateSuccess, handleMutateError } = useMutateHandlers();

  const postParticipateInMutation = useMutation({
    mutationFn: (roomId: number) => postParticipateIn(roomId),
    onSuccess: () => {
      handleMutateSuccess([QUERY_KEYS.PARTICIPATED_ROOM_LIST, QUERY_KEYS.OPENED_ROOM_LIST]);
    },
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  return { postParticipateInMutation };
};

export default useMutateParticipateIn;
