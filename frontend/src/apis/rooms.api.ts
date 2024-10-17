import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { ParticipantListInfo } from "@/@types/participantList";
import { Role, RoomInfo, RoomListInfo, SubmitRoomInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";

export const getParticipatedRoomList = async (
  includeClosed: boolean = false,
): Promise<RoomListInfo> => {
  const res = await apiClient.get({
    endpoint: `${API_ENDPOINTS.PARTICIPATED_ROOMS}?includeClosed=${includeClosed}`,
    errorMessage: MESSAGES.ERROR.GET_PARTICIPATED_ROOM_LIST,
  });

  return res;
};

export const getProgressRoomList = async (
  classification: string,
  page: number,
): Promise<RoomListInfo> => {
  const res = await apiClient.get({
    endpoint: `${API_ENDPOINTS.PROGRESS_ROOMS}?classification=${classification}&page=${page}`,
    errorMessage: MESSAGES.ERROR.GET_PROGRESS_ROOM_LIST,
  });

  return res;
};

export const getOpenedRoomList = async (
  classification: string,
  page: number,
): Promise<RoomListInfo> => {
  const res = await apiClient.get({
    endpoint: `${API_ENDPOINTS.OPENED_ROOMS}?classification=${classification}&page=${page}`,
    errorMessage: MESSAGES.ERROR.GET_OPENED_ROOM_LIST,
  });

  return res;
};

export const getClosedRoomList = async (
  classification: string,
  page: number,
): Promise<RoomListInfo> => {
  const res = await apiClient.get({
    endpoint: `${API_ENDPOINTS.CLOSED_ROOMS}?classification=${classification}&page=${page}`,
    errorMessage: MESSAGES.ERROR.GET_CLOSED_ROOM_LIST,
  });

  return res;
};

export const getRoomDetailInfo = async (id: number): Promise<RoomInfo> => {
  const res = await apiClient.get({
    endpoint: `${API_ENDPOINTS.ROOMS}/${id}`,
    errorMessage: MESSAGES.ERROR.GET_ROOM_DETAIL_INFO,
  });

  return res;
};

export const postCreateRoom = async (roomData: SubmitRoomInfo): Promise<void> => {
  return apiClient.post({
    endpoint: API_ENDPOINTS.ROOMS,
    body: roomData,
    errorMessage: MESSAGES.ERROR.POST_CREATE_ROOM,
  });
};

export const postParticipateIn = async (
  roomId: number,
  role: Role,
  matchingSize: number,
): Promise<void> => {
  return apiClient.post({
    endpoint: `${API_ENDPOINTS.PARTICIPATE_IN(roomId)}?role=${role}`,
    body: { matchingSize },
    errorMessage: MESSAGES.ERROR.POST_PARTICIPATE_IN,
  });
};

export const deleteParticipateIn = async (roomId: number): Promise<void> => {
  return apiClient.delete({
    endpoint: API_ENDPOINTS.PARTICIPATE_IN(roomId),
    errorMessage: MESSAGES.ERROR.DELETE_PARTICIPATE_IN,
  });
};

export const deleteParticipatedRoom = async (roomId: number): Promise<void> => {
  return apiClient.delete({
    endpoint: `${API_ENDPOINTS.ROOMS}/${roomId}`,
    errorMessage: MESSAGES.ERROR.DELETE_PARTICIPATED_ROOM,
  });
};

export const getParticipantList = async (roomId: number): Promise<ParticipantListInfo> => {
  const res = await apiClient.get({
    endpoint: API_ENDPOINTS.PARTICIPANT_LIST(roomId),
    errorMessage: MESSAGES.ERROR.GET_PARTICIPANT_LIST,
  });

  return res;
};
