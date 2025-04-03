import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AlarmAsRead } from "@/@types/alarm";
import { postMarkAlarmAsRead } from "@/apis/alarm.api";
import QUERY_KEYS from "@/apis/queryKeys";

const useMutateAlarm = () => {
  const queryClient = useQueryClient();

  const markAsRead = useMutation({
    mutationFn: ({ alarmId, alarmType }: AlarmAsRead) =>
      postMarkAlarmAsRead({ alarmId, alarmType }),
    onSuccess: () => {
      queryClient.fetchQuery({ queryKey: [QUERY_KEYS.ALARM_COUNT] });
    },
  });

  return { markAsRead };
};

export default useMutateAlarm;
