import { AlarmCount } from "@/@types/alaram";
import apiClient from "@/apis/apiClient";
import { API_ENDPOINTS } from "@/apis/endpoints";
import MESSAGES from "@/constants/message";

export const getAlarmCount = async (): Promise<AlarmCount> => {
  const res = await apiClient.get({
    endpoint: API_ENDPOINTS.ALARM_COUNT,
    errorMessage: MESSAGES.ERROR.GET_ALARM_COUNT,
  });

  return res;
};
