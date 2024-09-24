import useMutateHandlers from "./useMutateHandlers";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import QUERY_KEYS from "@/apis/queryKeys";
import { deleteParticipatedRoom } from "@/apis/rooms.api";

const useMutateRoom = () => {
  const queryClient = useQueryClient();
  const { handleMutateError } = useMutateHandlers();

  const deleteParticipatedRoomMutation = useMutation({
    mutationFn: (roomId: number) => deleteParticipatedRoom(roomId),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.PARTICIPATED_ROOM_LIST] });
    },
    onError: (error) => handleMutateError(error),
  });

  return { deleteParticipatedRoomMutation };
};

export default useMutateRoom;
