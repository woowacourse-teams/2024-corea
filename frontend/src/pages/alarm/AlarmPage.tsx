import { useNavigate } from "react-router-dom";
import useSelectedFeedbackData from "@/hooks/feedback/useSelectedFeedbackData";
import useMutateAlarm from "@/hooks/mutations/useMutateAlarm";
import { useFetchAlarmList } from "@/hooks/queries/useFetchAlarm";
import AlarmItem from "@/components/alarm/alarmItem/AlarmItem";
import EmptyAlarm from "@/components/alarm/emptyAlarm/EmptyAlarm";
import ContentSection from "@/components/common/contentSection/ContentSection";
import * as S from "@/pages/alarm/AlarmPage.style";
import { AlarmItemData } from "@/@types/alarm";

const AlarmPage = () => {
  const navigate = useNavigate();
  const { data } = useFetchAlarmList();
  const { markAsRead } = useMutateAlarm();
  const alarmListData = data?.data;
  const { handleSelectedFeedbackType, handleSelectedFeedback } = useSelectedFeedbackData();

  const handleNavigation = (actionType: string, interactionId: number) => {
    if (actionType === "FEEDBACK_CREATED") {
      handleSelectedFeedbackType("받은 피드백");
      handleSelectedFeedback(interactionId);
      navigate("/feedback");
      return;
    }

    // actionType이 REVIEW_COMPLETE, REVIEW_URGE, MATCH_COMPLETE, MATCH_FAIL
    navigate(`/rooms/${interactionId}`);
  };

  const handleAlarmClick = async (alarm: AlarmItemData) => {
    const { actionType, alarmId, isRead, interaction } = alarm;

    if (!isRead) {
      await markAsRead.mutateAsync({ alarmId, alarmType: "USER" });
    }

    handleNavigation(actionType, interaction.interactionId);
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
