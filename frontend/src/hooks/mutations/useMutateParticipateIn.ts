import useMutateHandlers from "./useMutateHandlers";
import { useMutation } from "@tanstack/react-query";
import useToast from "@/hooks/common/useToast";
import QUERY_KEYS from "@/apis/queryKeys";
import { postParticipateIn } from "@/apis/rooms.api";
import MESSAGES from "@/constants/message";

const useMutateParticipateIn = () => {
  const { handleMutateSuccess, handleMutateError } = useMutateHandlers();
  const { openToast } = useToast("success");

  const postParticipateInMutation = useMutation({
    mutationFn: (roomId: number) => postParticipateIn(roomId),
    onSuccess: () => {
      handleMutateSuccess([QUERY_KEYS.PARTICIPATED_ROOM_LIST, QUERY_KEYS.OPENED_ROOM_LIST]);
      openToast(MESSAGES.SUCCESS.POST_PARTICIPATE_IN);
    },
    onError: (error) => handleMutateError(error),
    networkMode: "always",
  });

  return { postParticipateInMutation };
};

export default useMutateParticipateIn;
