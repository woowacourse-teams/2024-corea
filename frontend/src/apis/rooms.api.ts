import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { ParticipantListInfo } from "@/@types/participantList";
import {
  Classification,
  CreateRoomInfo,
  MemberRole,
  RoomCreateRequest,
  RoomDetailResponse,
  RoomInfo,
  RoomListInfo,
  RoomStatus,
} from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";

export const getParticipatedRoomList = async (
  includeClosed: boolean = false,
): Promise<RoomListInfo> => {
  const { data } = await apiClient.get({
    endpoint: `${API_ENDPOINTS.PARTICIPATED_ROOMS}?includeClosed=${includeClosed}`,
    errorMessage: MESSAGES.ERROR.GET_PARTICIPATED_ROOM_LIST,
  });

  return data;
};

export const getProgressRoomList = async (
  classification: string,
  page: number,
): Promise<RoomListInfo> => {
  const { data } = await apiClient.get({
    endpoint: `${API_ENDPOINTS.PROGRESS_ROOMS}?classification=${classification}&page=${page}`,
    errorMessage: MESSAGES.ERROR.GET_PROGRESS_ROOM_LIST,
  });

  return data;
};

export const getOpenedRoomList = async (
  classification: string,
  page: number,
): Promise<RoomListInfo> => {
  const { data } = await apiClient.get({
    endpoint: `${API_ENDPOINTS.OPENED_ROOMS}?classification=${classification}&page=${page}`,
    errorMessage: MESSAGES.ERROR.GET_OPENED_ROOM_LIST,
  });

  return data;
};

export const getClosedRoomList = async (
  classification: string,
  page: number,
): Promise<RoomListInfo> => {
  const { data } = await apiClient.get({
    endpoint: `${API_ENDPOINTS.CLOSED_ROOMS}?classification=${classification}&page=${page}`,
    errorMessage: MESSAGES.ERROR.GET_CLOSED_ROOM_LIST,
  });

  return data;
};

export const getSearchRoomList = async (
  status: RoomStatus,
  classification: Classification,
  keywordTitle: string,
): Promise<Pick<RoomListInfo, "rooms">> => {
  const { data } = await apiClient.get({
    endpoint: `${API_ENDPOINTS.SEARCH_ROOMS}?status=${status}&classification=${classification.toUpperCase()}&keywordTitle=${keywordTitle}`,
    errorMessage: MESSAGES.ERROR.GET_SEARCH_ROOM_LIST,
  });

  return data;
};

export const getRoomDetailInfo = async (id: number): Promise<RoomInfo> => {
  const { data } = await apiClient.get({
    endpoint: `${API_ENDPOINTS.ROOMS}/${id}`,
    errorMessage: MESSAGES.ERROR.GET_ROOM_DETAIL_INFO,
  });

  return data;
};

export const postCreateRoom = async (roomData: RoomCreateRequest): Promise<RoomDetailResponse> => {
  return apiClient.post({
    endpoint: API_ENDPOINTS.ROOMS,
    body: roomData,
    errorMessage: MESSAGES.ERROR.POST_CREATE_ROOM,
  });
};

export const putEditRoom = async (roomData: CreateRoomInfo): Promise<void> => {
  return apiClient.put({
    endpoint: API_ENDPOINTS.ROOMS,
    body: roomData,
    errorMessage: MESSAGES.ERROR.PUT_EDIT_ROOM,
  });
};

export const postParticipateIn = async (
  roomId: number,
  role: MemberRole,
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
  const { data } = await apiClient.get({
    endpoint: API_ENDPOINTS.PARTICIPANT_LIST(roomId),
    errorMessage: MESSAGES.ERROR.GET_PARTICIPANT_LIST,
  });

  return data;
};
