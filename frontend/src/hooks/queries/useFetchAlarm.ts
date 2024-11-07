import { useQuery } from "@tanstack/react-query";
import { getAlarmCount } from "@/apis/alarm.api";
import QUERY_KEYS from "@/apis/queryKeys";

export const useFetchAlarmCount = () => {
  return useQuery({
    queryKey: [QUERY_KEYS.ALARM_COUNT],
    queryFn: getAlarmCount,
  });
};
