import { useNavigate } from "react-router-dom";
import useMutateAlarm from "@/hooks/mutations/useMutateAlarm";
import { useFetchAlarmList } from "@/hooks/queries/useFetchAlarm";
import AlarmItem from "@/components/alarm/alarmItem/AlarmItem";
import EmptyAlarm from "@/components/alarm/emptyAlarm/EmptyAlarm";
import ContentSection from "@/components/common/contentSection/ContentSection";
import * as S from "@/pages/alarm/AlarmPage.style";
import { AlarmItemData } from "@/@types/alarm";

const NAVIGATION_PATHS = {
  REVIEW_COMPLETE: (interactionId: number) => `/rooms/${interactionId}`,
  REVIEW_URGE: (interactionId: number) => `/rooms/${interactionId}`,
  MATCH_COMPLETE: (interactionId: number) => `/rooms/${interactionId}`,
  MATCH_FAIL: (interactionId: number) => `/rooms/${interactionId}`,
  FEEDBACK_CREATED: () => "/feedback",
} as const;

const AlarmPage = () => {
  const navigate = useNavigate();
  const { data } = useFetchAlarmList();
  const { markAsRead } = useMutateAlarm();
  const alarmListData = data?.data;

  const handleAlarmClick = async (alarm: AlarmItemData) => {
    const { actionType, alarmId, isRead, interaction } = alarm;

    if (!isRead) {
      await markAsRead.mutateAsync({ alarmId, alarmType: "USER" });
    }

    const path = NAVIGATION_PATHS[actionType];
    if (path) {
      navigate(path(interaction.interactionId));
    }
  };

  if (!alarmListData?.length) {
    return <EmptyAlarm />;
  }

  return (
    <S.Layout>
      <ContentSection title="알림 내역">
        <S.AlarmList>
          {alarmListData?.map((alarm) => (
            <AlarmItem key={alarm.alarmId} alarm={alarm} onAlarmClick={handleAlarmClick} />
          ))}
        </S.AlarmList>
      </ContentSection>
    </S.Layout>
  );
};

export default AlarmPage;
