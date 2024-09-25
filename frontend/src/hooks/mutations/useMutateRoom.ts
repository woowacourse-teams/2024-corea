import useToast from "../common/useToast";
import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { CreateRoomInfo } from "@/@types/roomInfo";
import QUERY_KEYS from "@/apis/queryKeys";
import { deleteParticipateIn, postCreateRoom, postParticipateIn } from "@/apis/rooms.api";
import MESSAGES from "@/constants/message";

const useMutateRoom = () => {
  const queryClient = useQueryClient();
  const { openToast } = useToast("success");
  const { handleMutateError } = useMutateHandlers();

  const postCreateRoomMutation = useMutation({
    mutationFn: (roomData: CreateRoomInfo) => postCreateRoom(roomData),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST] });
      openToast(MESSAGES.SUCCESS.POST_CREATE_ROOM);
    },
    onError: (error) => handleMutateError(error),
  });

  const postParticipateInMutation = useMutation({
    mutationFn: (roomId: number) => postParticipateIn(roomId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST] });
    },
    onError: (error) => handleMutateError(error),
  });

  const deleteParticipateInMutation = useMutation({
    mutationFn: (roomId: number) => deleteParticipateIn(roomId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST] });
    },
    onError: (error) => handleMutateError(error),
  });

  return { postCreateRoomMutation, postParticipateInMutation, deleteParticipateInMutation };
};

export default useMutateRoom;
