import { useQuery, useQueryClient, useSuspenseQuery } from "@tanstack/react-query";
import { getAlarmCount, getAlarmList } from "@/apis/alarm.api";
import QUERY_KEYS from "@/apis/queryKeys";

export const useFetchAlarmCount = (enabled: boolean = false) => {
  return useQuery({
    queryKey: [QUERY_KEYS.ALARM_COUNT],
    queryFn: getAlarmCount,
    enabled,
    refetchInterval: 60 * 1000,
  });
};

export const useFetchAlarmList = () => {
  const queryClient = useQueryClient();

  return useSuspenseQuery({
    queryKey: [QUERY_KEYS.ALARM_LIST],
    queryFn: async () => {
      const result = await getAlarmList();
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.ALARM_COUNT] });
      return result;
    },
    refetchInterval: 60 * 1000,
  });
};
