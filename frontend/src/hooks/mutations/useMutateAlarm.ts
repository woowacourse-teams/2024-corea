import { useMutation, useQueryClient } from "@tanstack/react-query";
import useMutateHandlers from "@/hooks/mutations/useMutateHandlers";
import { AlarmAsRead } from "@/@types/alaram";
import { postMarkAlarmAsRead } from "@/apis/alarm.api";
import QUERY_KEYS from "@/apis/queryKeys";

const useMutateAlarm = () => {
  const { handleMutateError } = useMutateHandlers();
  const queryClient = useQueryClient();

  const markAsRead = useMutation({
    mutationFn: ({ alarmId, alarmType }: AlarmAsRead) =>
      postMarkAlarmAsRead({ alarmId, alarmType }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.ALARM_LIST] });
    },
    onError: (error) => handleMutateError(error),
  });

  return { markAsRead };
};

export default useMutateAlarm;
