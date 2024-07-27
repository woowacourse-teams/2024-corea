import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { RoomInfo, RoomListInfo } from "@/@types/roomInfo";
import MESSAGES from "@/constants/message";

export const getParticipatedRoomList = async (): Promise<RoomListInfo> => {
  const res = await apiClient.get({
    endpoint: API_ENDPOINTS.PARTICIPATED_ROOMS,
    errorMessage: MESSAGES.ERROR.GET_PARTICIPATED_ROOM_LIST,
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
