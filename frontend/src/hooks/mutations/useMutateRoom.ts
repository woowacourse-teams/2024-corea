import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { deleteParticipateIn, deleteParticipatedRoom, postParticipateIn } from "@/apis/rooms.api";

const useMutateRoom = () => {
  const queryClient = useQueryClient();
  const { handleMutateError } = useMutateHandlers();

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

  const deleteParticipatedRoomMutation = useMutation({
    mutationFn: (roomId: number) => deleteParticipatedRoom(roomId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST] });
    },
    onError: (error) => handleMutateError(error),
  });

  return { postParticipateInMutation, deleteParticipateInMutation, deleteParticipatedRoomMutation };
};

export default useMutateRoom;
