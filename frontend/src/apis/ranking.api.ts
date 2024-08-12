import apiClient from "./apiClient";
import { API_ENDPOINTS } from "./endpoints";
import { RankingAllData } from "@/@types/ranking";
import MESSAGES from "@/constants/message";

export const getRankingList = async (): Promise<RankingAllData> => {
  const res = await apiClient.get({
    endpoint: `${API_ENDPOINTS.RANKING}`,
    // errorMessage: MESSAGES.ERROR.GET_OPENED_ROOM_LIST,
  });

  return res;
};
