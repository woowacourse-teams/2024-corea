import useToast from "../common/useToast";
import { useMutation } from "@tanstack/react-query";
import { CreateRoomInfo, MemberRole, RoomCreateRequest } from "@/@types/roomInfo";
import {
  deleteParticipateIn,
  deleteParticipatedRoom,
  postCreateRoom,
  postParticipateIn,
  putEditRoom,
} from "@/apis/rooms.api";
import MESSAGES from "@/constants/message";

const useMutateRoom = () => {
  const { showToast } = useToast();

  const postCreateRoomMutation = useMutation({
    mutationFn: (roomData: RoomCreateRequest) => postCreateRoom(roomData),
    onSuccess: () => {
      showToast(MESSAGES.SUCCESS.POST_CREATE_ROOM, "success");
    },
  });

  const putEditRoomMutation = useMutation({
    mutationFn: (roomData: CreateRoomInfo) => putEditRoom(roomData),
    onSuccess: () => {
      showToast(MESSAGES.SUCCESS.PUT_EDIT_ROOM, "success");
    },
  });

  const postParticipateInMutation = useMutation({
    mutationFn: ({
      roomId,
      role,
      matchingSize,
    }: {
      roomId: number;
      role: MemberRole;
      matchingSize: number;
    }) => postParticipateIn(roomId, role, matchingSize),
  });

  const deleteParticipateInMutation = useMutation({
    mutationFn: (roomId: number) => deleteParticipateIn(roomId),
  });

  const deleteParticipatedRoomMutation = useMutation({
    mutationFn: (roomId: number) => deleteParticipatedRoom(roomId),
  });

  return {
    postCreateRoomMutation,
    putEditRoomMutation,
    postParticipateInMutation,
    deleteParticipateInMutation,
    deleteParticipatedRoomMutation,
  };
};

export default useMutateRoom;
