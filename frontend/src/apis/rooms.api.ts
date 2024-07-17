import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { RoomInfo } from "@/@types/roomInfo";

export const getRoomList = async (): Promise<{ rooms: RoomInfo[] }> => {
  const res = await apiClient<{ rooms: RoomInfo[] }>({
    method: "get",
    url: `${API_ENDPOINTS.ROOMS}`,
  });

  return res.data;
};

export const getRoomDetailInfo = async (id: number): Promise<RoomInfo> => {
  const res = await apiClient<RoomInfo>({
    method: "get",
    url: `${API_ENDPOINTS.ROOMS}/${id}`,
  });

  return res.data;
};
