import useToast from "../common/useToast";
import useMutateHandlers from "./useMutateHandlers";
import { useMutation } from "@tanstack/react-query";
import { CreateRoomInfo, Role } from "@/@types/roomInfo";
import {
  deleteParticipateIn,
  deleteParticipatedRoom,
  postCreateRoom,
  postParticipateIn,
} from "@/apis/rooms.api";
import MESSAGES from "@/constants/message";

const useMutateRoom = () => {
  const { openToast } = useToast("success");
  const { handleMutateError } = useMutateHandlers();

  const postCreateRoomMutation = useMutation({
    mutationFn: (roomData: CreateRoomInfo) => postCreateRoom(roomData),
    onSuccess: () => {
      openToast(MESSAGES.SUCCESS.POST_CREATE_ROOM);
    },
    onError: (error) => handleMutateError(error),
  });

  const postParticipateInMutation = useMutation({
    mutationFn: ({ roomId, role }: { roomId: number; role: Role }) =>
      postParticipateIn(roomId, role),

    onError: (error) => handleMutateError(error),
  });

  const deleteParticipateInMutation = useMutation({
    mutationFn: (roomId: number) => deleteParticipateIn(roomId),

    onError: (error) => handleMutateError(error),
  });

  const deleteParticipatedRoomMutation = useMutation({
    mutationFn: (roomId: number) => deleteParticipatedRoom(roomId),

    onError: (error) => handleMutateError(error),
  });

  return {
    postCreateRoomMutation,
    postParticipateInMutation,
    deleteParticipateInMutation,
    deleteParticipatedRoomMutation,
  };
};

export default useMutateRoom;
