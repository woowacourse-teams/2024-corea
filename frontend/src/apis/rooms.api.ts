import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { RoomInfo, RoomListInfo } from "@/@types/roomInfo";

export const getParticipatedRoomList = async (): Promise<{ roomInfo: RoomInfo[] }> => {
  const res = await apiClient<{ roomInfo: RoomInfo[] }>({
    method: "get",
    url: API_ENDPOINTS.PARTICIPATED_ROOMS,
  });

  return res.data;
};

export const getOpenedRoomList = async (
  classification: string,
  page: number,
): Promise<RoomListInfo> => {
  const res = await apiClient({
    method: "get",
    url: `${API_ENDPOINTS.OPENED_ROOMS}?classification=${classification}&page=${page}`,
  });

  return res.data;
};

export const getClosedRoomList = async (
  classification: string,
  page: number,
): Promise<RoomListInfo> => {
  const res = await apiClient({
    method: "get",
    url: `${API_ENDPOINTS.CLOSED_ROOMS}?classification=${classification}&page=${page}`,
  });

  return res.data;
};

export const getRoomDetailInfo = async (id: number): Promise<RoomInfo> => {
  const res = await apiClient<{ roomInfo: RoomInfo }>({
    method: "get",
    url: `${API_ENDPOINTS.ROOMS}/${id}`,
  });

  return res.data.roomInfo;
};
