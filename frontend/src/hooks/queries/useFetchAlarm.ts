import { useQuery, useQueryClient } from "@tanstack/react-query";
import { getAlarmCount, getAlarmList } from "@/apis/alarm.api";
import QUERY_KEYS from "@/apis/queryKeys";

export const useFetchAlarmCount = (enabled: boolean = false) => {
  return useQuery({
    queryKey: [QUERY_KEYS.ALARM_COUNT],
    queryFn: getAlarmCount,
    enabled,
    refetchInterval: 60 * 30,
  });
};

export const useFetchAlarmList = () => {
  const queryClient = useQueryClient();

  return useQuery({
    queryKey: [QUERY_KEYS.ALARM_LIST],
    queryFn: async () => {
      const result = await getAlarmList();
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.ALARM_COUNT] });
      return result;
    },
    refetchInterval: 60 * 30,
  });
};
