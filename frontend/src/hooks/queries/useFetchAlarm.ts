import { useQuery } from "@tanstack/react-query";
import { getAlarmCount, getAlarmList } from "@/apis/alarm.api";
import QUERY_KEYS from "@/apis/queryKeys";

export const useFetchAlarmCount = (enabled: boolean = false) => {
  return useQuery({
    queryKey: [QUERY_KEYS.ALARM_COUNT],
    queryFn: getAlarmCount,
    enabled,
  });
};

export const useFetchAlarmList = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.ALARM_LIST],
    queryFn: getAlarmList,
  });
};
