import { AlarmAsRead, AlarmCount, AlarmListData } from "@/@types/alaram";
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

export const getAlarmList = async (): Promise<AlarmListData> => {
  const res = await apiClient.get({
    endpoint: API_ENDPOINTS.ALARM_LISt,
    errorMessage: MESSAGES.ERROR.GET_ALARM_LIST,
  });

  return res;
};

export const postMarkAlarmAsRead = async ({ alarmId, alarmType }: AlarmAsRead): Promise<void> => {
  return apiClient.post({
    endpoint: API_ENDPOINTS.ALARM_CHECKED,
    body: { alarmId, alarmType },
    errorMessage: MESSAGES.ERROR.POSt_ALARM_CHECKED,
  });
};
